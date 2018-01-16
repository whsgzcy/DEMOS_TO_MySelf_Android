'use strict';
var TF = TF || {
	// 将ros的四元数转换成stage的角度方向, createjs stage only
	// params：
	//	orientation
	// return:	
	//	方向的角度值
	quaternionToTheta: (orientation) => {
		var q0 = orientation.w;
    	var q1 = orientation.x;
    	var q2 = orientation.y;
    	var q3 = orientation.z;
    	// Canvas rotation is clock wise and in degrees
    	return -Math.atan2(2 * (q0 * q3 + q1 * q2), 1 - 2 * (q2 * q2 + q3 * q3)) * 180.0 / Math.PI;
    },

    // quaternion -> yaw
    // params: 
    // 	orientation
    // return:
    //	yaw(radian)
    quaternionToYaw: (orientation, ignoreXY) => {
    	var rotation = orientation;
    	var numerator;
    	var denominator;
    	if (ignoreXY)
    	{
    		numerator = 2*rotation.w*rotation.z;
			denominator = 1-2*Math.pow(rotation.z,2);
    	}
    	else
    	{
    		numerator = 2*(rotation.w*rotation.z+rotation.x*rotation.y);
			denominator = 1-2*(Math.pow(rotation.y,2)+Math.pow(rotation.z,2));
    	}
		var yaw = Math.atan2(numerator, denominator);
		return yaw;
    },

    quaternionToRoll: (orientation) => {
    	var rotation = orientation;
		var numerator = 2*(rotation.w*rotation.x+rotation.y*rotation.z);
		var denominator = 1-2*(Math.pow(rotation.x,2)+Math.pow(rotation.y,2));
		var yaw = Math.atan2(numerator, denominator);
		return yaw;
    },

    // frame_id map -> px
	rosToPx: (pos) => {
		if (!DATA.mapStage)
		{
			console.warn('[WARN]map not available');
			return;
		}
		var height = DATA.mapStage.image.height;
		var x = Math.round((pos.x - DATA.mapMsg.info.origin.position.x) / DATA.mapMsg.info.resolution 
			* DATA.mapStage.scaleX);
		var y = Math.round((height-(pos.y - DATA.mapMsg.info.origin.position.y) / DATA.mapMsg.info.resolution) 
			* DATA.mapStage.scaleY);
		return {
			x: x - DATA.mapStage.regX * DATA.mapStage.scaleX,
			y: y - DATA.mapStage.regY * DATA.mapStage.scaleY
		};
	},

	// px -> frame_id map
	pxToRos: (pos) => {
		var height = DATA.mapStage.image.height;
		return {
			x: (pos.x + DATA.mapStage.regX * DATA.mapStage.scaleX) * DATA.mapMsg.info.resolution / DATA.mapStage.scaleX + DATA.mapMsg.info.origin.position.x,
			y: (height - DATA.mapStage.regY - pos.y / DATA.mapStage.scaleY) * DATA.mapMsg.info.resolution + DATA.mapMsg.info.origin.position.y
		};
	},

	thetaToQuaternion: (theta) => {
		return {
			x: 0,
			y: 0,
			z: Math.sin(theta/2),
			w: Math.cos(theta/2)
		};
	},

	getTfMat: (transform) => {
		var translation = transform.translation;
		var yaw = TF.quaternionToYaw(transform.rotation);
		var sinYaw = Math.sin(yaw);
		var cosYaw = Math.cos(yaw);
		var tx = translation.x;
		var ty = translation.y;
		return $M([[cosYaw, -sinYaw, tx],
				   [sinYaw,  cosYaw, ty],
				   [	 0,       0,  1]]);
	},

	// get docking begin waypoint pose from dock's pose
	// params: 
	// 	1.dockPose: dock's pose in map
	// 	2.distance: distance between dock and docking begin waypoint 
	// return: docking begin waypoint's pose in map
	getDockingBeginPose: (dockPose, distance) => {
		var yaw = TF.quaternionToYaw(dockPose.orientation, true);
		var offset = {
			x: distance * Math.cos(yaw),
			y: distance * Math.sin(yaw)
		};
		dockPose.position.x -= offset.x;
		dockPose.position.y -= offset.y;
		return dockPose;
	},

	localPlanOdomToMap: (posMsg) => {
		var posMsgMap = {
			poses: []
		};
		if (!DATA.tfMsg.map2odom)
		{
			console.warn('[WARN]No Tf msg from map to odom available');
			return posMsgMap;
		}
		if (!isTfValid(DATA.tfMsg.map2odom.header.stamp, posMsg.header.stamp, 2.0))
		{
			console.warn('[WARN]Tf msg from map to odom out of date');
			return posMsgMap;
		}
		var tfMat = TF.getTfMat(DATA.tfMsg.map2odom.transform);
		for (var i = 0; i < posMsg.poses.length; i++)
		{
			var p = $V([posMsg.poses[i].pose.position.x, posMsg.poses[i].pose.position.y, 1]);	
			var result = tfMat.multiply(p);
			var pos = {
				pose: {
					position: {
						x: result.elements[0],
						y: result.elements[1]
					}
				}
			}
			posMsgMap.poses.push(pos);
		}
		return posMsgMap;
	},

	footprintOdomToMap: (polygonMsg) => {
		var polygonMsgMap = {
			polygon: {
				points: []
			}
		};
		if (!DATA.tfMsg.map2odom)
		{
			console.warn('[WARN]No Tf msg from map to odom available');
			return polygonMsgMap;
		}
		if (!isTfValid(DATA.tfMsg.map2odom.header.stamp, polygonMsg.header.stamp, 2.0))
		{
			console.warn('[WARN]Tf msg from map to odom out of date');
			return polygonMsgMap;	
		}
		var tfMat = TF.getTfMat(DATA.tfMsg.map2odom.transform);
		for (var i = 0; i < polygonMsg.polygon.points.length; i++)
		{
			var p = $V([polygonMsg.polygon.points[i].x, polygonMsg.polygon.points[i].y, 1]);	
			var result = tfMat.multiply(p);
			polygonMsgMap.polygon.points.push({
				x: result.elements[0],
				y: result.elements[1]
			});
		}
		return polygonMsgMap;
	},

	laserScanBase_laserToMap: (laserScanMsg) => {
		var skipNum = 10;
		var laserScanMsgMap = [];
		if (DATA.tfMsg.hasOwnProperty('map2base_laser'))
		{
			var tfs = [DATA.tfMsg.map2base_laser];		
		}
		else
		{
			var tfs = [DATA.tfMsg.map2odom, DATA.tfMsg.odom2base_footprint, 
				   DATA.tfMsg.base_footprint2base_link, DATA.tfMsg.base_link2base_laser];	
		}
		if (DATA.laserScanMsg.header.frame_id === 'base_footprint')
		{
			if (DATA.tfMsg.hasOwnProperty('map2base_footprint'))
			{
				tfs = [DATA.tfMsg.map2base_footprint];
			}
			else
			{
				tfs = [DATA.tfMsg.map2odom, DATA.tfMsg.odom2base_footprint];	
			}
		}
		var isTfAvailable = true;
		for (var i = 0; i < tfs.length; i++)
		{
			if (!tfs[i])
			{
				isTfAvailable = false;
				break;
			}
		}
		if (!isTfAvailable)
		{
			console.warn('[WARN]One or more Tf msg from map to base_laser not available');
			return laserScanMsgMap;
		}
		var isValid = true;
		for (var i = 0; i < tfs.length; i++)
		{
			isValid = isValid && isTfValid(tfs[i].header.stamp, laserScanMsg.header.stamp, 2.0);
		}
		if (!isValid)
		{
			console.warn('[WARN]One or more Tf msg from map to base_laser out of date');
			return laserScanMsgMap;
		}
		var tfMat = $M([[1, 0, 0],
					    [0, 1, 0],
					    [0, 0, 1]]);
		for (var i = 0; i < tfs.length; i++)
		{
			tfMat = tfMat.multiply(TF.getTfMat(tfs[i].transform));
		}
		// check if laser upside down
		if (DATA.tfMsg.base_link2base_laser)
		{
			var rotation = DATA.tfMsg.base_link2base_laser.transform.rotation;
			var isUpsideDown = isLaserUpsideDown(rotation);
		}
		else
		{
			// TODO: replace this
			var isUpsideDown = 0;
		}
		for (var i = 0; i < laserScanMsg.ranges.length; i += skipNum)
		{
			if (laserScanMsg.ranges[i] === 'inf' || laserScanMsg.ranges[i] < laserScanMsg.range_min
				|| laserScanMsg.ranges[i] > laserScanMsg.range_max)
			{
				continue;
			}
			var angle = laserScanMsg.angle_min + laserScanMsg.angle_increment * i;
			var pos = [
				laserScanMsg.ranges[i] * Math.cos(angle),
				laserScanMsg.ranges[i] * Math.sin(angle) * Math.pow(-1,isUpsideDown),
				1
			];	
			var posVec = $V(pos);
			var result = tfMat.multiply(posVec);
			laserScanMsgMap.push({
				x: result.elements[0],
				y: result.elements[1]
			});
		}
		return laserScanMsgMap;
	}
};

function isTfValid(tfStamp, msgStamp, duration)
{
	if (!tfStamp)
	{
		return true;
	}
	var secs = Math.abs(tfStamp.secs - msgStamp.secs);
	var nsecs = Math.abs(tfStamp.nsecs - msgStamp.nsecs);
	var t = secs + nsecs / Math.pow(10,9);
	return (t < duration);
}

function isLaserUpsideDown(rotation)
{
	var roll = TF.quaternionToRoll(rotation);
	if (Math.abs(roll-Math.PI)<0.01)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}