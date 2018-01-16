var UI = UI || {
	toolBar: ['move', 'zoomIn', 'zoomOut', 'pencilTool', 'rubber', 
		'undo', 'redo', 'confirm', 'point', 'line', 'circle', 'rect'],

	display: (val) => {
		if (val.hasOwnProperty('property'))
		{
			var topicName = val.property + 'Topic';
			var stageName = val.property + 'Stage';
			var topic = DATA.topic[topicName];
			var stageObj = DATA[stageName];
			if (val.value)
			{
				var msgName = val.property + 'Msg';
				if (msgName === 'mapMsg')
				{
					DATA.stage.stage.addChild(stageObj);
					return;
				}
				topic.subscribe((message) => {
					DATA[msgName] = message;
				});
			}
			else
			{
				if (stageName === 'waypointsStage')
				{
					topic.unsubscribe();
					for (var key in stageObj)
					{
						if (DATA.waypointsStage.hasOwnProperty(key))
						{
							var waypointsContainers = DATA.waypointsStage[key];
							for (var i = 0; i < waypointsContainers.length; i++)
							{
								DATA.stage.stage.removeChild(waypointsContainers[i]);
							}
						}
					}
					return;
				}
				else if (stageName === 'mapStage')
				{
					DATA.stage.stage.removeChild(stageObj);
					return;
				}
				else
				{
					topic.unsubscribe();
					DATA.stage.stage.removeChild(stageObj);	
				}
			}
		}
	},

	dispMap: (message) => {
		if (DATA.mapStage)
		{	
			DATA.stage.stage.removeAllChildren();
			DATA.robotStage = null;
		}
		var innerCanvas = document.createElement('canvas');
		var innerCtx = innerCanvas.getContext('2d');
		innerCanvas.width = message.info.width;
 		innerCanvas.height = message.info.height;
 		var imageData = innerCtx.createImageData(message.info.width, message.info.height);
 		for ( var row = 0; row < message.info.height; row++) {
			for ( var col = 0; col < message.info.width; col++) {
				// determine the index into the map data
				var mapI = col + ((message.info.height - row - 1) * message.info.width);
				// determine the value
				var data = message.data[mapI];
			  	var i = (col + (row * message.info.width)) * 4;
			  	if (data === 100) 
			  	{
				  	imageData.data[i] = 40;
				  	imageData.data[++i] = 53;
				  	imageData.data[++i] = 147;
					imageData.data[++i] = 255;
			  	} 
			  	else if (data === 0) 
			  	{
					imageData.data[i] = 232;
				  	imageData.data[++i] = 234;
				  	imageData.data[++i] = 246;
				  	imageData.data[++i] = 63;
			  	} 
			  	else 
			  	{
					imageData.data[i] = 245;
				  	imageData.data[++i] = 245;
				  	imageData.data[++i] = 245;
				  	imageData.data[++i] = 5; //0
			  	}
			}// inner for
		}// for

    	var rMap = message.info.width / message.info.height;
	    var width = DATA.stage.width;
	    var height = DATA.stage.height;
	    if (DATA.stage.width > DATA.stage.height)
	    {
	        width = DATA.stage.height * rMap;
	        height = DATA.stage.height;
	    }
	    else
	    {
	        width = DATA.stage.width;
	        height = DATA.stage.width / rMap;
	    }
	    var scale = {
    		x: width / DATA.stage.width || 1.0,
    		y: height / DATA.stage.height || 1.0
    	}
		innerCtx.putImageData(imageData, 0, 0);
		var bitmap = new createjs.Bitmap(innerCanvas);
		bitmap.scaleX = DATA.stage.width / message.info.width * scale.x;
		bitmap.scaleY = DATA.stage.height / message.info.height * scale.y;
		bitmap.regX = (width - DATA.stage.width) / 2 || 0;
		bitmap.regY = (height - DATA.stage.height) / 2 || 0;
		DATA.mapScaleStage = {
			x: bitmap.scaleX,
			y: bitmap.scaleY
		};
		DATA.mapStage = bitmap;
		DATA.stage.stage.addChild(DATA.mapStage);
		if (DATA.loading)
		{
			DATA.loading = false;
		}
	},

	dispWaypoints: (message) => {
		// remove waypoints from stage
		if (DATA.waypointsStage)
		{
			for (var key in DATA.waypointsStage)
			{
				if (DATA.waypointsStage.hasOwnProperty(key))
				{
					var waypointsContainers = DATA.waypointsStage[key];
					for (var i = 0; i < waypointsContainers.length; i++)
					{
						DATA.stage.stage.removeChild(waypointsContainers[i]);
					}
				}
			}
		}

		// undisplay if in gmapping mode
		if (DATA.rosMode === NAV.RosMode.Gmapping
			|| DATA.rosMode === NAV.RosMode.Converting)
		{
			return;
		}

		var waypointContainers = {
			map: [],
		};
		// waypoints list in side bar
		var waypointSidebarList = '';
		var waypointTrajAdd = '';
		for (var i = 0; i < message.waypoints.length; i++)
		{
			var waypoint = message.waypoints[i];
			// assemble waypoint list in sidebar
			var type = waypoint.header.frame_id;
			var waypointHtml = getWaypointDom(waypoint.name, type);
			waypointSidebarList += waypointHtml.wpList;
			waypointTrajAdd += waypointHtml.wpForTrajAdd;
			// display on map
			var waypointType = waypoint.header.frame_id;
			var wpXY = TF.rosToPx(waypoint.pose.position);
			// only display map type
			if (waypointType !== 'map')
			{
				continue;
			}
			var wpGraphics = new createjs.Graphics();
			var wpContainer = ICON[waypointType]({});
			wpContainer.x = wpXY.x;
			wpContainer.y = wpXY.y;
			wpContainer.rotation = TF.quaternionToTheta(waypoint.pose.orientation);
			wpContainer.name = waypoint.name; // bind name
			DATA.stage.stage.addChild(wpContainer);
			waypointContainers[waypointType].push(wpContainer);
			// TODO: handle wp click
			wpContainer.on('click', UI.wpClickHandle); 
		}
		DATA.waypointsStage = waypointContainers;
		// display waypoint list in side bar
		$('.waypoint_list').remove();
		$('.sidebar_site').append(waypointSidebarList);
		// display waypoint list in trajectory add page
		$('.wpsInTrajAdd').remove();
		
		$('#waypointTrajAdd').append(waypointTrajAdd);
		var waypointListDom = $('.sidebar_site');
		var height = waypointListDom.css('height');
		if (!isEqual(height, 60))
		{
			height = (message.waypoints.length + 2) * 60;
            waypointListDom.css('height', height);
		}
	},

	dispTrajectories: (message) => {
		var trajSidebarList = '';
		var isDockAddDone = false;
		for (var i = 0; i < message.trajectories.length; i++)
		{
			var traj = message.trajectories[i];
			trajSidebarList += getTrajDom(traj.name);
			if (traj.name === NAV.DockingEndTrajName)
			{
				isDockAddDone = true;
			}
		}
		$('.trajectory_list').remove();
		$('.sidebar_route').append(trajSidebarList);
		var sidebarRoute = $('.sidebar_route');
        var sidebarRouteSpan = $('#sidebar_route_span');
        if (!isEqual(sidebarRoute.css('height'), 60))
		{
			var height = (DATA.trajectoriesMsg.trajectories.length + 2) * 60;
            sidebarRoute.css('height', height);
		}
		// undisplay loading info if dock added
		if (isDockAddDone)
		{
			if (DATA.loading.key === 'addDock')
			{
				DATA.loading = false;
			}
		}
	},

	dispRobot: (message) => {
		var pos = TF.rosToPx({
				x: message.position.x,
				y: message.position.y
		});
		if (!pos)
		{
			return;
		}
		var ori = TF.quaternionToTheta(message.orientation);
		if (!DATA.robotPoseStage)
		{
			DATA.robotPoseStage = ICON.robot({});
			DATA.stage.stage.addChild(DATA.robotPoseStage);
		}
		else
		{
			if (DATA.stage.stage.contains(DATA.robotPoseStage));
			{
				DATA.stage.stage.addChild(DATA.robotPoseStage);
			}
		}
		DATA.robotPoseStage.x = pos.x;
		DATA.robotPoseStage.y = pos.y;
		DATA.robotPoseStage.rotation = ori;
	},

	dispGlobalPlan: (message) => {
		if (DATA.globalPlanStage)
		{
			DATA.stage.stage.removeChild(DATA.globalPlanStage);
		}
		DATA.globalPlanStage = ICON.globalPlan(message, {});
		DATA.stage.stage.addChild(DATA.globalPlanStage);
	},

	dispLocalPlan: (message) => {
		if (DATA.localPlanStage)
		{
			DATA.stage.stage.removeChild(DATA.localPlanStage);
		}
		var msgMap = TF.localPlanOdomToMap(message);
		DATA.localPlanStage = ICON.localPlan(msgMap, {
			strokeColor: '#e91e63'
		});
		DATA.stage.stage.addChild(DATA.localPlanStage);
	},

	dispFootprint: (message) => {
		if (DATA.footprintStage)
		{
			DATA.stage.stage.removeChild(DATA.footprintStage);
		}
		if (DATA.topic.footprintTopic.name === '/move_base/local_costmap/footprint')
		{
			var msgMap = TF.footprintOdomToMap(message);	
		}
		else
		{
			var msgMap = message;
		}
		DATA.footprintStage = ICON.footprint(msgMap, {
			strokeColor: '#8bc34a'
		});
		DATA.stage.stage.addChild(DATA.footprintStage);
	},

	dispLaserScan: (message) => {
		if (DATA.laserScanStage)
		{
			DATA.stage.stage.removeChild(DATA.laserScanStage);
		}
		var laserScanPoints = TF.laserScanBase_laserToMap(message);
		DATA.laserScanStage = ICON.laserScan(laserScanPoints, {
			fillColor: '#ff9800'
		});
		DATA.stage.stage.addChild(DATA.laserScanStage);
	},

	updateMapList: (mapList) => {
		$('.otherMap').remove();
		var currentMap = mapList[0];
		for (var i = 1; i < mapList.length; i++)
		{
			var child = getMapDom(mapList[i]);
			$('#mapList').append(child);
		}
		$('#currentMapName').text(currentMap);
		$('#mapping_div_input').val(currentMap);
	},

	rosModeHandle: (mode) => {
		if (mode === NAV.RosMode.Gmapping)
		{
			var mapnav = $('.map_nav');
			if (!isEqual(mapnav.css('height'), 35))
            {
                mapnav.css('height', 35);
            }

            if (DATA.mappingStatus === NAV.CmdEnum.Gmapping)
            {
            	$('#currentMapName').text('保存地图');	
            }
            else if (DATA.mappingStatus === NAV.CmdEnum.SaveMapEdit)
            {
            	$('#currentMapName').text('保存修改');	
            	// display brush tools
            	$("#footer_button").css("left", 0);
            	if (!DATA.loading)
            	{
            		DATA.loading = false;
            	}
            }
            else  
            {
            	
            }
		}
		else if (mode === NAV.RosMode.Converting)
		{
			$('#currentMapName').text('切换中');
		}
		else if (mode === NAV.RosMode.Navigation)
		{
			if (!DATA.mapList)
			{
				return;
			}
			var currentMap = DATA.mapList[0];
			$('#currentMapName').text(currentMap);
			$("#footer_button").css("left", '-100%');
			if (!DATA.loading)
			{
				DATA.loading = false;
			}
		}
		else
		{

		}
	},

	navCtrlStatusHandle: (navCtrlStatus) => {
		if (navCtrlStatus.status === PARAMS.NavCtrlStatus.idling)
		{
			$('.waypoint_list').css('backgroundColor', 'transparent');
		}
		else if (navCtrlStatus.status === PARAMS.NavCtrlStatus.running)
		{
			var currentWaypoint = 'waypoint_' + navCtrlStatus.waypoint_name;
			var waypoints = $('.waypoint_list');
			for (var i = 0; i < waypoints.length; i++)
			{
				var name = $(waypoints[i]).attr('id');
				if (name === currentWaypoint)
				{

					$(waypoints[i]).css('backgroundColor', '#e8eaf6');
				}
				else 
				{
					$(waypoints[i]).css('backgroundColor', 'transparent');	
				}
			}
		}
		else
		{
			// TODO: error, paused, completed, cancelled, sub_cancelled
		}
	},

	zoomIn: (scale) => {
		if (DATA.mapStage.scaleX > 2 * DATA.mapScaleStage.x 
			|| DATA.mapStage.scaleY > 2 * DATA.mapScaleStage.y)
		{
			console.log('[INFO]Max scale');
			return;
		}
		// scale bitmap
		DATA.mapStage.scaleX *= scale;
		DATA.mapStage.scaleY *= scale;		
		var width = DATA.mapStage.scaleX * DATA.mapStage.image.width;
		var height = DATA.mapStage.scaleY * DATA.mapStage.image.height;
		DATA.mapStage.regX = Math.round(0.5 * (width - DATA.mapStage.image.width * DATA.mapScaleStage.x));
		DATA.mapStage.regY = Math.round(0.5 * (height - DATA.mapStage.image.height * DATA.mapScaleStage.y));
		// scale map edit
		for (var i = 0; i < DATA.mapEditStageList.length; i++)
		{
			var child = DATA.mapEditStageList[i];
			if (child.name === 'mapEdit')
			{
				child.scaleX *= scale;
				child.scaleY *= scale;
				console.log(child); 
			}
		}	
	},

	zoomOut: () => {
		DATA.mapStage.regX = 0;
		DATA.mapStage.regY = 0;
		DATA.mapStage.scaleX = DATA.mapScaleStage.x;
		DATA.mapStage.scaleY = DATA.mapScaleStage.y;
	},

	brushStatusHandle: (tool) => {
		// reset other tools
		for (var i = 0; i < UI.toolBar.length; i++)
		{
			if (UI.toolBar[i] !== tool)
			{
				var domName = '#' + UI.toolBar[i];
				$(domName).css('backgroundColor', 'transparent');
				DATA.stage.stage.removeEventListener('pressmove', PAINT[UI.toolBar[i]]);
				DATA.stage.stage.removeEventListener('click', PAINT[UI.toolBar[i]]);
				DATA.stage.stage.removeEventListener('pressup', PAINT.pressupHandle);
			}
		}
		var selectedDom = $('#' + tool);
		var enable = true;
		if (selectedDom.css('backgroundColor') === 'transparent' 
			|| selectedDom.css('backgroundColor') === 'rgba(0, 0, 0, 0)')
		{
			selectedDom.css('backgroundColor', '#e8eaf6');
		}
		else
		{
			selectedDom.css('backgroundColor', 'transparent');
			enable = false;
		}
		switch(tool)
		{
			case 'move':
				//TODO
				return;
				if (enable)
				{
					DATA.stage.stage.addEventListener('pressmove', PAINT.move);	
				}
				else
				{
					DATA.stage.stage.removeEventListener('pressmove', PAINT.move);
				}
				break;
			case 'zoomIn':
				selectedDom.css('backgroundColor', '#e8eaf6');
				//UI.zoomIn(1.2);
				break;
			case 'zoomOut':
				selectedDom.css('backgroundColor', '#e8eaf6');
				//UI.zoomOut();
				break;
			case 'pencilTool':
				if (enable)
				{
					$('#pencilTool_content').css('height', 240);
				}
				else
				{
					$('#pencilTool_content').css('height', 0);
				}
				break;
			case 'rubber':
				if (enable)
				{
					DATA.stage.stage.addEventListener('pressmove', PAINT.rubber);
					DATA.stage.stage.addEventListener('pressup', PAINT.pressupHandle);	
				}
				else
				{
					DATA.stage.stage.removeEventListener('pressmove', PAINT.rubber);
					DATA.stage.stage.removeEventListener('pressup', PAINT.pressupHandle);
				}
				break;
			case 'undo':
				var mapEditContainer = DATA.mapEditStageList.pop();
				DATA.mapEditObstacleList.pop();
				if (!mapEditContainer)
				{
					break;
				}
				DATA.stage.stage.removeChild(mapEditContainer);
				break;
			case 'redo':
				break;
			case 'confirm':
				console.log('[INFO]Confirm map_edit');
				$('#currentMapName').text('保存中');
				DATA.loading = '保存中';
				for (var i = 0; i < DATA.mapEditObstacleList.length; i++)
				{
					var obstacle = DATA.mapEditObstacleList[i];
					NAV.pubMapEditObstacle(obstacle);
				}
				DATA.mapEditStageList = [];
				DATA.mapEditObstacleList = [];
				setTimeout(function(){
					NAV.pubCmdString('map_edit_done');
				}, 500);

				break;
			default:
				$('#pencilTool_content').css('height', 0);
				var img = `url(../image/${tool}.png)`;
				$('#pencilTool').css('background-color', '#e8eaf6');
				$('#pencilTool').css('background-image', img);
				switch (tool)
				{
					case 'point':
						if (enable)
						{
							DATA.stage.stage.addEventListener('pressmove', PAINT.point);
							DATA.stage.stage.addEventListener('pressup', PAINT.pressupHandle);	
						}
						else
						{
							DATA.stage.stage.removeEventListener('pressmove', PAINT.point);
							DATA.stage.stage.removeEventListener('pressup', PAINT.pressupHandle);
						}
						break;
					case 'line':
						if (enable)
						{
							DATA.stage.stage.addEventListener('click', PAINT.line);
						}
						else
						{
							DATA.stage.stage.removeEventListener('click', PAINT.line);
						}
						break;
					case 'circle':
						if (enable)
						{
							DATA.stage.stage.addEventListener('click', PAINT.circle);
						}
						else
						{
							DATA.stage.stage.removeEventListener('click', PAINT.circle);
						}
						break;
					case 'rect':
						if (enable)
						{
							DATA.stage.stage.addEventListener('click', PAINT.rect);
						}
						else
						{
							DATA.stage.stage.removeEventListener('click', PAINT.rect);
						}
						break;
				}
				break;
		}
	},

	poseEstimate: () => {
		DATA.stage.stage.addEventListener('click', PAINT.pose);	
	},

	undispBrushTool: () => {
		if (DATA.mapStage.contains(DATA.brushToolStage))
		{
			DATA.mapStage.removeChild(DATA.brushToolStage);
		}
	},

	dispSelectedWaypoints: () => {
		$('.selectedWp').remove();
		var selectedWpHtml = '';
		for (var i = 0; i < DATA.selectedWaypoints.length; i++)
		{
			var fullName = DATA.selectedWaypoints[i];
			selectedWpHtml += getWaypointSelectedDom(fullName);
		}
		$('#selected_wps_div').append(selectedWpHtml);
	},

	updatePowerStatus: (powerStatus) => {
		powerStatus = parseFloat(powerStatus);
		var powerIcon = $('.battery_display');
		powerIcon.css('height', powerStatus * 100);
		if (DATA.chargeStatus === PARAMS.ChargeStatus.charging)
		{
			return;
		}
		if (powerStatus < PARAMS.PowerWarnThreshold)
		{
			if (!DATA.powerAlarm)
			{
				DATA.powerAlarm = true;
				powerIcon.css('background-color', '#ff0000');
				ALERT.warn({
					title: '电量低',
					text: `当前电量${(powerStatus*100).toFixed()}%, 请及时充电`
				});
			}
			
		}
		else 
		{
			powerIcon.css('background-color', '#3f51b5');
		}
	},

	updateChargeStatus: (chargeStatus) => {
		chargeStatus = parseInt(chargeStatus);
		console.log(`[INFO]charge status ${chargeStatus}`);
		var powerIcon = $('.battery_display');
		switch (chargeStatus)
		{
			// not contact
			case PARAMS.ChargeStatus.uncontact:
				powerIcon.css('background-color', '#3f51b5');
				break;
			// contact
			case PARAMS.ChargeStatus.contact:
				powerIcon.css('background-color', '#00ff00');
				break;
			// abnormal voltage
			case PARAMS.ChargeStatus.volAbnormal:
				powerIcon.css('background-color', '#ffff00');
				ALERT.error({
					title: '充电异常',
					text: '充电桩电压异常,请检查充电桩'
				});
				break;
			// charging
			case PARAMS.ChargeStatus.charging:
				powerIcon.css('background-color', '#00ff00');
				ALERT.info({
					title: '正在充电',
					text: '充电状态正常'
				});
				break;
			default:
				break;
		}

	},

	diagnosticsMsgHandle: (diagnosticsList) => {
		var diagnosticsHtml = ['', '', '', '']; // html for status OK, warn, error and stale
		for (var i = 0; i < diagnosticsList.length; i++)
		{
			var level = diagnosticsList[i].level + 1;
			diagnosticsHtml[level] += getDiagnosticsDom(diagnosticsList[i]);
		}
		$('.errors').remove();
		var isEmpty = true;
		for (var i = diagnosticsHtml.length-1; i > 0; i--)
		{
			isEmpty = isEmpty && (diagnosticsHtml[i] === '')
			$('#diagnosis').append(diagnosticsHtml[i]);		
		}
		if (isEmpty)
		{
			var emptyPattern = `<div class="errors">
					            	<span class="errors_left">未检测到错误</span>
					            	<span class="errors_right"></span>
					        	</div>`;
			$('#diagnosis').append(emptyPattern);        	
		}
	},

	loading: (info) => {
		if (info)
		{
			var hint = info;
			if ((typeof info) === 'object')
			{
				if (info.hasOwnProperty('key'))
				{
					hint = info.info;
				}
			}
			// display loading
			$('#loading_info').text(hint);
			$('#loading').css('display', 'block');
			return;
		}
		$('#loading').css('display', 'none');
	},

	robotModelHandle: (model) => {
		if (PARAMS.Extensions.indexOf(model) === -1)
		{
			$('#extensions').hide();
			return;
		}
		switch (model)
		{
			case 'ster':
			case 'ster2':
				$('#extensions_title').text('汽化过氧化氢消毒机器人');
				$('#login').css('display', 'block');
				// $('#extension_span').text('汽化过氧化氢消毒机器人');
				break;
			case 'tofflon':
				$('#login').css('display', 'block');
				break;
			default:
				// undisplay login page
				break;
		}
	},

	softwareVersionHandle: (version) => {

	},

	browserInfoHandle: (browserInfo) => {
		
	},

	wpClickHandle: (event) => {
		var waypointName = event.currentTarget.name;
		var shortName = waypointName.split('_').slice(1).join('_');
		ALERT.confirm({
			title: `站点${shortName}`,
			text: `确定要导航至站点${shortName}?`,
			callback: function(){
				NAV.navCtrl(waypointName, 1);
			}
		});
		return;
	},

	mapClickHandle: (event) => {
		console.log('change page');
		$.mobile.changePage("#myPage", "slideup"); 
		return;
		console.log('map click');
		$.mobile.changePage("#share", "slideup"); 

	}
};

// get waypoint dom
// params: 
// 	1.fullName: waypoint's full name with prefix
// 	2.type: waypoint's type, frame_id
// return:
//	{
//		wpList: Html for waypoint list in side bar,
//		wpForTrajAdd: Html for trajectory add page	
// } 
function getWaypointDom(fullName, type)
{
	var name = fullName.split('_').slice(1).join('_'); 
	if (!name)
	{
		name = fullName;
	}
	var patternForWpList = `<div data-role="none" class="waypoint_list" id="${'waypoint_'+fullName}">
	                    <span class="sidebar_site_type_logo" onclick='dispWaypointDetail(this)'>
	                    	<img src="image/${type}.png" width="100%"/>
	                    </span>
	                    <span class="waypoint_name" onclick='exeWaypoint(this)'>
	                    	${name}
	                    </span>
	                    <span class="sidebar_site_type_confirm" onclick='delWaypoint(this)'>
	                    	<img src="image/delete.png" width="100%"/>
	                    </span>
                  		</div>`;
    var patternForTraj = `<option class="wpsInTrajAdd" value="${fullName}">${name}</option>`;
    return {
    	wpList: patternForWpList,
    	wpForTrajAdd: patternForTraj
    };

}

// get selected waypoint of trajectory in traj add page
function getWaypointSelectedDom(fullName)
{
	var prefix = fullName.split('_')[0].trim();
	var type;
	for (var key in NAV.WaypointPrefix)
	{
		if (NAV.WaypointPrefix[key] == prefix)
		{
			type = key;
			break;
		}
	}
	var name = fullName.split('_').slice(1).join('_'); 
	if (!name)
	{
		name = fullName;
	}
	var pattern = `<li style="height: 60px" id="${fullName}" class="selectedWp">
                    	<span class="sidebar_site_type_logo">
                            <img src="image/${type}.png" width="100%"/>
                        </span>
                        <span class="waypoint_name">
                            ${name}
                        </span>
                        <span class="del_waypoint_selected" onclick='delWaypointSelected(this)'>
                            <img src="image/delete.png" width="100%"/>
                        </span>
                   </li>`;
    return pattern;
}

function getTrajDom(fullName)
{
	var icon = 'trajectory';
	var name = fullName;
	if (fullName.split('_')[0] === 'dock')
	{
		icon = 'dock';
		name = fullName.split('_').slice(1).join('_'); 
	}
	var pattern = `<div data-role="none" class="trajectory_list" id="${'traj_'+fullName}">
                    <span class="traj_icon" onclick='dispTrajDetail(this)'>
                    	<img src="image/${icon}.png" width="100%"/>
                    </span>
                    <span class="traj_name" onclick='exeTraj(this)'>
                    	${name}
                    </span>
                    <span class="traj_delete" onclick='delTraj(this)'>
                    	<img src="image/delete.png" width="100%"/>
                    </span>
                  </div>`;
    return pattern;
}

function getMapDom(name)
{
	var icon = 'delete';
	if (name === 'master')
	{
		icon = 'ban_delete';
	}
	var pattern = `<li class='otherMap' id='${name}'>
					<span class='mapName' onclick='changeScene(this)'>
						${name}
					</span>
				    <span class='mapDelIcon' onclick='delScene(this)'><img src="image/${icon}.png" width="100%"/>
				    </span>
				   </li>`;	
	return pattern;
}

function getDiagnosticsDom(diagnosticsInfo)
{
	var backgroundColor = "background: #ffffff";
	var color = "color: #3b6dde";
	switch (diagnosticsInfo.level)
	{
		case PARAMS.DiagnosticsLevel.Warn:
			backgroundColor = "background: #fffde7";
			color = "color: #000000";
			break;
		case PARAMS.DiagnosticsLevel.Error:
			backgroundColor = "background: #ffebee";
			color = "color: #000000";
			break;
		case PARAMS.DiagnosticsLevel.Stale:
			backgroundColor = "background: #fafafa";
			color = "color: #000000";
			break;
		default:
			break;
	}
	var index = diagnosticsInfo.name.indexOf('/', 1);
	var shortName = diagnosticsInfo.name.substr(index);
	var pattern = `<div class="errors" id='${diagnosticsInfo.name}' onclick='dispDiagnosticsDetail(this)' style="${backgroundColor}">
                	<span class="errors_left" style="${color}">${shortName}</span>
                	<span class="errors_right" style="${color}">${diagnosticsInfo.message}</span>
            	   </div>`;
    return pattern;
}

function changeScene(dom)
{
	clickFeedback($(dom).parent());
	var name = $(dom).parent().attr('id');
	NAV.manipulateScene(NAV.CmdEnum.MapUpdate, name);
	console.log(`[INFO]switching to map: ${name}`);
	DATA.loading = '切换中';
}

function delScene(dom)
{
	clickFeedback($(dom).parent());
	var name = $(dom).parent().attr('id');
	if (name === 'master')
	{
		ALERT.warn({
			title: '删除警告',
			text: '不允许删除master版本地图'
		});
		return;
	}
	NAV.manipulateScene(NAV.CmdEnum.MapDelete, name);
	console.log(`[INFO]deleting map: ${name}`);
}

function toFullName(name, type)
{
	var fullName = name;
	var prefix = NAV.WaypointPrefix[type];
	if (prefix)
	{
		fullName = prefix + '_' + fullName;
	}
	return fullName;
}

function dispWaypointDetail(dom)
{
	clickFeedback($(dom).parent());
	var imgSrc = $(dom).children()[0].src.split('/');
	var type = imgSrc[imgSrc.length-1];
	type = type.substring(0, type.length-4);
	// var prefix = NAV.WaypointPrefix[type];
	var name = $(dom).next().text().trim();
	var fullName = toFullName(name, type);
	var waypoint = getWaypointByName(fullName);
	console.log(`[INFO]waypoint:\n-type: ${type};\n-name: ${name}.`);
	updateWaypointDetailPage(waypoint);
	$("#site_details").popup("open");
}

function exeWaypoint(dom)
{
	clickFeedback($(dom).parent());
	var name = $(dom).text().trim();
	var imgSrc = $(dom).prev().children()[0].src.split('/');
	var type = imgSrc[imgSrc.length-1];
	type = type.substring(0, type.length-4);
	// var prefix = NAV.WaypointPrefix[type];
	var fullName = toFullName(name, type);
	NAV.navCtrl(fullName, 1);
}

function delWaypoint(dom)
{
	clickFeedback($(dom).parent());
	if (DATA.navCtrlStatus.status !== PARAMS.NavCtrlStatus.idling)
	{
		ALERT.warn({
			title: '导航中',
			text: '请停止当前任务后再删除该站点'
		});
		return;
	}
	var bros = $(dom).prevAll();
	// waypoint type
	var imgSrc = $(bros[1]).children()[0].src.split('/');
	var type = imgSrc[imgSrc.length-1];
	type = type.substring(0, type.length-4);
	// var prefix = NAV.WaypointPrefix[type];
	// waypoint name
	var name = $(bros[0]).text().trim();
	var fullName = toFullName(name, type);
	NAV.delWaypoint(fullName);
}

function delWaypointSelected(dom)
{
	var waypointName = $(dom).parent().attr('id');
	var index = DATA.selectedWaypoints.indexOf(waypointName);
	DATA.selectedWaypoints.splice(index, 1);
	UI.dispSelectedWaypoints();
}

function dispTrajDetail(dom)
{
	clickFeedback($(dom).parent());
	var trajInfo = getTrajInfo(dom);
	console.log(`[INFO]trajectory:\n-type: ${trajInfo.type};\n-name: ${trajInfo.name}.`);
	var traj = getTrajectoryByName(trajInfo.name);
	updateTrajDetailPage(traj);
	$("#line_details").popup("open");
}

function exeTraj(dom)
{
	clickFeedback($(dom).parent());
	var trajInfo = getTrajInfo(dom);
	if (trajInfo.name === NAV.DockingEndTrajName)
	{
		if (DATA.chargeStatus === PARAMS.ChargeStatus.uncontact)
		{
			ALERT.warn({
				title: '充电已结束',
				text: '结束充电命令将忽略'
			});
			return;
		}
	}
	else if (trajInfo.name === NAV.DockingBeginTrajName)
	{
		if (DATA.chargeStatus !== PARAMS.ChargeStatus.uncontact)
		{
			ALERT.warn({
				title: '正在充电',
				text: '开始充电命令将忽略'
			});
			return;
		}
	}
	NAV.navCtrl(trajInfo.name, 1);
}

function delTraj(dom)
{
	clickFeedback($(dom).parent(), {
		duration: 500
	});
	if (DATA.navCtrlStatus.status !== PARAMS.NavCtrlStatus.idling)
	{
		ALERT.warn({
			title: '导航中',
			text: '请停止当前任务后再删除该路线'
		});
		return;
	}
	var trajInfo = getTrajInfo(dom);
	NAV.delTrajectory(trajInfo.name);
}

// get trajectory name and type by the child dom element of div
// params: 
// 	1.dom: child dom element of traj div
// return: {
// 	name: name,
//	type: 'cutomer'/'dock'}
function getTrajInfo(dom)
{
	var name = $(dom).parent().attr('id').substring(5);
	var type = 'customer';
	if (name.startsWith('dock'))
	{
		type = 'dock';
	}
	return {
		name: name,
		type: type
	};
}

function updateWaypointDetailPage(waypoint)
{
	$('#wp_detail_name').text(waypoint.name);
	$('#wp_detail_frame_id').text(waypoint.frame_id);
	$('#wp_detail_failure_mode').text(waypoint.failure_mode);
	$('#wp_detail_close_enough').text(waypoint.close_enough.toFixed(3));
	$('#wp_detail_goal_timeout').text(waypoint.goal_timeout.toFixed(3));
	$('#wp_detail_pose_posX').text(waypoint.pose.position.x.toFixed(3));
	$('#wp_detail_pose_posY').text(waypoint.pose.position.y.toFixed(3));
	$('#wp_detail_pose_posZ').text(waypoint.pose.position.z.toFixed(3));
	$('#wp_detail_pose_oriX').text(waypoint.pose.orientation.x.toFixed(3));
	$('#wp_detail_pose_oriY').text(waypoint.pose.orientation.y.toFixed(3));
	$('#wp_detail_pose_oriZ').text(waypoint.pose.orientation.z.toFixed(3));
	$('#wp_detail_pose_oriW').text(waypoint.pose.orientation.w.toFixed(3));
	var yaw = TF.quaternionToYaw(waypoint.pose.orientation);
	var yawDeg = yaw / Math.PI * 180;
	$('#wp_detail_pose_yaw').text(`${yaw.toFixed(3)} RAD / ${yawDeg.toFixed(3)} DEG`);
}

function updateTrajDetailPage(trajectory)
{
	var ul = $('.line_details_ul');
	ul.children().remove();
	var trajDetailHtml = ''
	for (var i = 0; i < trajectory.waypoints.length; i++)
	{
		var waypoint = trajectory.waypoints[i];
		trajDetailHtml += `<li>
                    		<span class="line_details_ul_left">${i}：</span>
                    		<span class="line_details_ul_right">${waypoint.name}</span>
                			</li>`;
	}
	ul.append(trajDetailHtml);
}

function dispDiagnosticsDetail(dom)
{
	// TODO: handle diagnostics
}

// click feedback
// params:
// 	1.target
//	2.options:
//		colorClicked: #e8eaf6(default)
//		colorAfter: transparent(default)
//		duration: 200(default)
function clickFeedback(target, options)
{
	var colorClicked = '#e8eaf6';
	var colorAfter = 'transparent';
	var duration = 200;
	if (options)
	{
		colorClicked = options.colorClicked || '#e8eaf6';
		colorAfter = options.colorAfter || 'transparent';
		duration = options.duration || 200;
	}
	target.css('backgroundColor', colorClicked);
	setTimeout(function(){
		target.css('backgroundColor', colorAfter);
	}, duration);
}

function undispWpMenu()
{
	var bomb_boxsObj = $('#bomb_boxs');
	bomb_boxsObj.css('opacity', '0');
	bomb_boxsObj.css('left', -125);
	bomb_boxsObj.css('top', -125);
	for (var i = 0; i < 8; i++)
	{
		var domName = `#bomb_boxs_${i+1}`;
		$(domName).css('opacity', '0');
	}
	UI.wpMenuDisp = false;
}

//删除站点链接
function undispWpBombbox4()
{
	swal({
	    title: "确定删除吗？",
	    text: "您将无法恢复这个文件！",
		imageUrl: "image/executes.png",
//	    type: "warning",
	    showCancelButton: true,
	    confirmButtonColor: "#3b6dde",
 	    cancelButtonText: "否",
	    confirmButtonText: "是",
	    closeOnConfirm: false,
	},
	function(){
	    swal("Deleted!", "已删除", "success");
	  });
}