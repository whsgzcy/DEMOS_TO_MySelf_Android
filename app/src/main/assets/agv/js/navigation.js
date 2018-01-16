var PARAMS = PARAMS || {
	DefaultPose: {
    	position: {
    			x: 0,
    			y: 0,
    			z: 0
    	},
    	orientation: {
    			x: 0,
    			y: 0,
    			z: 0,
    			w: 1
    	}
    },
    // cmd_vel for docking
    CmdVel: 0.05,
    // backward moving distance before navigation
    SailDis: 0.30,
    // distance between dock and docking begin waypoint
    DockingBeginDis: 0.35,
    // threshold for low power warning
    PowerWarnThreshold: 0.2,

    ChargeStatus: {
    	uncontact: 0,
    	contact: 1,
    	volAbnormal: 2,
    	charging: 3
    },

    NavCtrl: {
    	stop: 0,
    	start: 1,
    	pause: 2
    },

    NavCtrlStatus: {
    	error: -1,
    	idling: 0,
    	running: 1,
    	paused: 2,
    	completed: 3,
    	cancelled: 4,
    	sub_cancelled: 5
    },

    NetworkMode: {
    	ap: 'ap',
    	wifi: 'wifi'
    },

    RobotStatus: {
    	MappingStatus: 'mappingStatus',
    	LastNetworkSetting: 'lastNetworkSetting'
    },

    Extensions: ['ster', 'ster2', 'tofflon'],

    DiagnosticsLevel: {
    	Warn: 1,
    	Error: 2,
    	Stale: 3
    },
    AngleAdjustWaypoint: 'pubsuber_charge_adjust'
};

var NAV = NAV || {
	CmdEnum: {
        Navigation: "navigation",
        Gmapping: "gmapping",
        Cancel: "cancel",
        Converting: "converting",
        GamppingPose: "gmapping_pose",
        SaveMap: "save_map",
        SaveMapEdit: "save_map_edit",
        SaveAsMap: "save_as_map",
        SaveAsMapEdit: "save_as_map_edit",
        LoadMap: "load_map",
        LoadMapEdit: "load_map_edit",
        Userauth: "user_auth",
        MapSelect: "dbparam-select",  //查询地图
        MapDelete: "dbparam-delete",  //删除地图
        MapUpdate: "dbparam-update", // 切换地图
        MapInsert: "dbparam-insert", // 添加地图
        Update: "update",
        RoslogDelete: "roslog-delete",
        RoslogSelect: "roslog-select",
        Version: "version",
    },

    RosMode: {
    	Gmapping: 'gmapping',
    	Navigation: 'navigation',
    	Converting: 'converting'
    },

    WaypointMode: {
    	Map: 'map',
    	Timer: 'timer',
    	Puber: 'publisher',
    	Suber: 'subscriber',
    	Pubsuber: 'pubsuber',
    	Looper: 'looper',
    	CmdVelSetSub: 'cmd_vel_set_sub',
    	CmdVel: 'cmd_vel',
    	Shell: 'shell',
    	SoundPlay: 'sound_play',
    	InitialPose: 'initial_pose',
    	ScanMaker: 'scan_marker',
    	ShellString: 'shell_string',
    	Pallet: 'pallet'
    },

    SoundPlayMode: {
    	Stop: 'STOP',
    	Start: 'START',
    	Once: 'ONCE'
    },

    WaypointPrefix: {
    	map: 'map',
    	timer: 'timer',
    	publisher: 'pub',
    	subscriber: 'sub',
    	looper: 'loop',
    	pubsuber: 'pubsuber',
    	cmd_vel_set_sub: 'velSet',
    	cmd_vel: 'vel',
    	shell: 'shell',
    	sound_play: 'sound',
    	initial_pose: 'pose',
    	scan_marker: 'scanMarker',
    	shell_string: 'shellStr',
    	pallet: 'pallet'
    },

    ManualCtrlVel: {
    	linear: 0.4,
    	angular: 0.8
    },

    WaypointsForDock:
    {
    	pubsuber_auto_charge: {
    		name: 'pubsuber_auto_charge',
    		frame_id: 'pubsuber',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: '3',
    		pose: PARAMS.DefaultPose
    	},

    	vel_forward: {
    		name: 'vel_forward',
    		frame_id: 'cmd_vel',
    		close_enough: PARAMS.CmdVel,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	vel_stop: {
    		name: 'vel_stop',
    		frame_id: 'cmd_vel',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	vel_backward: {
    		name: 'vel_backward',
    		frame_id: 'cmd_vel',
    		close_enough: - PARAMS.CmdVel,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_bumper_on: {
    		name: 'pub_bumper_on',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_bumper_off: {
    		name: 'pub_bumper_off',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_estop_on: {
    		name: 'pub_estop_on',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_estop_off: {
    		name: 'pub_estop_off',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_estop_auto: {
    		name: 'pub_estop_auto',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_light_r: {
    		name: 'pub_light_r',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_light_g: {
    		name: 'pub_light_g',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_light_b: {
    		name: 'pub_light_b',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	pub_light_y: {
    		name: 'pub_light_y',
    		frame_id: 'publisher',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	},

    	sound_sound_on: {
    		name: 'sound_sound_on',
    		frame_id: 'sound_play',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'START',
    		pose: PARAMS.DefaultPose
    	},

    	sound_sound_off: {
    		name: 'sound_sound_off',
    		frame_id: 'sound_play',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'STOP',
    		pose: PARAMS.DefaultPose
    	},

    	sound_sound_play_once: {
    		name: 'sound_sound_play_once',
    		frame_id: 'sound_play',
    		close_enough: 0,
    		goal_timeout: 0,
    		failure_mode: 'ONCE',
    		pose: PARAMS.DefaultPose
    	},

    	// scanMarker pattern
    	scanMarker_scan: {
    		name: 'scanMarker_scan',
    		frame_id: 'scan_marker',
    		close_enough: 0.9,
    		goal_timeout: 0,
    		failure_mode: 'LOOP',
    		pose: PARAMS.DefaultPose
    	},

    	timer_sail: {
    		name: 'timer_sail',
    		frame_id: 'timer',
    		close_enough: 0,
    		goal_timeout: (PARAMS.SailDis / PARAMS.CmdVel),
    		failure_mode: 'NONE',
    		pose: PARAMS.DefaultPose
    	}
    },

    DockingBeginTrajName: 'dock_begin_charge',
   	DockingBegin: ['map_dockBegin', 'pub_estop_off', 'scanMarker_scan_6', 'scanMarker_scan_6','pub_light_y', 'sound_sound_on',
   		 'vel_forward', 'pubsuber_auto_charge', 'vel_stop', 'sound_sound_off', 'pub_estop_off', 'pub_light_b'],

    DockingEndTrajName: 'dock_end_charge',
    DockingEnd: ['', 'pub_light_y', 'pub_estop_off', 'pub_bumper_off', 'sound_sound_on', 'vel_backward',
    	'timer_sail', 'vel_stop', 'sound_sound_off', 'pub_bumper_on', 'pub_estop_auto', 'pub_light_g'],

	init: (url) => {
		NAV.ros = new ROSLIB.Ros();
		NAV.ros.connect(url);
		NAV.ros.on('connection', () => {
			console.log(`[INFO]Connected to rosbridge ${url}.`);
			DATA.ros = NAV.ros;
		});
		NAV.ros.on('close', () => {
			console.log(`[INFO]Rosbridge server ${url} closed.`);
			ALERT.error({
				title: '网络错误',
				text: 'Rosbridge连接失败'
			});
		});
		NAV.ros.on('error', () => {
			console.error(`[ERROR]Connection error.`);
		});
	},

	getRobotStatus: (...rest) => {
		var robotStatusClient = new ROSLIB.Service({
			ros: NAV.ros,
			name: '/rosnodejs/robot_status',
			serviceType: 'rosapi/Publishers'
		});
		if (rest.length === 0)
		{
			var request = new ROSLIB.ServiceRequest({
				topic: ''
			});
		}
		else
		{
			var requestData = '';
			for (var i = 0; i < rest.length; i++)
			{
				requestData += `${rest[i]}, `;
			}
			var request = new ROSLIB.ServiceRequest({
				topic: requestData
			});
		}
		robotStatusClient.callService(request, (response) => {
			for (var i = 0; i < response.publishers.length; i++)
			{
				var key = response.publishers[i].split(':')[0].trim();
				var status = response.publishers[i].split(':')[1].trim();
				switch (key)
				{
					case PARAMS.RobotStatus.MappingStatus:
						DATA.mappingStatus = status;
						break;
					case PARAMS.RobotStatus.LastNetworkSetting:
						DATA.lastNetworkSetting = status;
						break;
					default:
						break;
				}
			}
		})
	},

	dispMapAndWps: (map) => {
		var mapTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: map || '/map_edit',
			messageType: 'nav_msgs/OccupancyGrid'
		});
		DATA.topic['mapTopic'] = mapTopic;
		mapTopic.subscribe((message) => {
			DATA.mapMsg = message;
			NAV.dispWaypoints();
		});
	},

	dispWaypoints: () => {
		var wpTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/waypoints',
			messageType: 'yocs_msgs/WaypointList'
		});
		DATA.topic['waypointsTopic'] = wpTopic;
		wpTopic.subscribe((message) => {
			console.log(`[INFO]Got waypoints`);
			DATA.waypointsMsg = message;
		});
	},

	dispTrajectories: () => {
		var trajTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/trajectories',
			messageType: 'yocs_msgs/TrajectoryList'
		});
		trajTopic.subscribe((message) => {
			console.log('[INFO]Got trajectories.');
			DATA.trajectoriesMsg = message;
		});
	},

	dispRobot: () => {
		var robotPoseTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/robot_pose',
			messageType: 'geometry_msgs/Pose'
		});
		DATA.topic['robotPoseTopic'] = robotPoseTopic;
		robotPoseTopic.subscribe((message) => {
			DATA.robotPoseMsg = message;
		});
	},

	dispGlobalPlan: () => {
		var globalPlanTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/move_base/NavfnROS/plan',
			messageType: 'nav_msgs/Path'
		});
		DATA.topic['globalPlanTopic'] = globalPlanTopic;
		globalPlanTopic.subscribe((message) => {
			DATA.globalPlanMsg = message;
		});
	},

	dispLocalPlan: () => {
		var localPlanTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/move_base/DWAPlannerROS/local_plan',
			messageType: 'nav_msgs/Path'
		});
		DATA.topic['localPlanTopic'] = localPlanTopic;
		localPlanTopic.subscribe((message) => {
			DATA.localPlanMsg = message;
		});
	},

	dispFootprint: (costmap) => {
		var footprintTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: costmap || '/move_base/global_costmap/footprint',
			messageType: 'geometry_msgs/PolygonStamped'
		});
		DATA.topic['footprintTopic'] = footprintTopic;
		footprintTopic.subscribe((message) => {
			DATA.footprintMsg = message;
		});
	},

	dispLaserScan: () => {
		var laserScanTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/scan_rectified',
			messageType: 'sensor_msgs/LaserScan',
			throttle_rate: 200
		});
		DATA.topic['laserScanTopic'] = laserScanTopic;
		laserScanTopic.subscribe((message) => {
			DATA.laserScanMsg = message;
		});
	},

	dispLocalCostmap: () => {
		var localCostmapTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/move_base/local_costmap/costmap',
			messageType: 'nav_msgs/OccupancyGrid'
		});
		DATA.topic['localCostmapTopic'] = localCostmapTopic;
		localCostmapTopic.subscribe((message) => {
			localCostmapTopic.unsubscribe();
		});
	},

	listenTf: (useTf2) => {
		var useTf2 = (useTf2 === undefined) ? true : useTf2;
		if (useTf2)
		{
			var tfClient = new ROSLIB.TFClient({
				ros: NAV.ros,
				fixedFrame: 'map',
				angularThres: 0.01,
				transThres: 0.01
			});
			tfClient.subscribe('odom', (tf) => {
				DATA.tfMsg['map2odom'] = {
					header: {
						stamp: null
					},
					transform: tf
				};
			});
			tfClient.subscribe('base_footprint', (tf) => {
				DATA.tfMsg['map2base_footprint'] = {
					header: {
						stamp: null
					},
					transform: tf
				};
			});
			tfClient.subscribe('base_laser', (tf) => {
				DATA.tfMsg['map2base_laser'] = {
					header: {
						stamp: null
					},
					transform: tf
				};
			});
			var tfClient2 = new ROSLIB.TFClient({
				ros: NAV.ros,
				fixedFrame: 'base_link',
				angularThres: 0.01,
				transThres: 0.01
			});
			tfClient2.subscribe('base_laser', (tf) => {
				DATA.tfMsg['base_link2base_laser'] = {
					header: {
						stamp: null
					},
					transform: tf
				};
			});
		}
		else
		{
			var tfTopic = new ROSLIB.Topic({
				ros: NAV.ros,
				name: '/tf',
				messageType: 'tf2_msgs/TFMessage',
				// throttle_rate: 50
			});
			tfTopic.subscribe((message) => {
				for (var i = 0; i < message.transforms.length; i++)
				{
					var frame_id = message.transforms[i].header.frame_id;
					var child_frame_id = message.transforms[i].child_frame_id;
					if (frame_id.startsWith('/'))
					{
						frame_id = frame_id.split('/')[1];
					}
					if (child_frame_id.startsWith('/'))
					{
						child_frame_id = child_frame_id.split('/')[1];
					}
					var tfName = frame_id + '2' + child_frame_id; // eg: map2odom
					DATA.tfMsg[tfName] = message.transforms[i];
				}
			});
		}
	},

	sendInitialPose: (pose) => {
		var initialPoseTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/initialpose',
			messageType: 'geometry_msgs/PoseWithCovarianceStamped'
		});
		var covariance = [];
		for (var i = 0; i < 36; i++)
		{
			covariance[i] = 0;
		}
		covariance[0] = 0.25;
		covariance[7] = 0.25;
		covariance[35] = Math.pow((Math.PI/12), 2);
		var msg = new ROSLIB.Message({
			header: {
				frame_id: 'map'
			},
			pose: {
				pose: pose,
				covariance: covariance
			}
		});
		initialPoseTopic.publish(msg);
		console.log('[INFO]setting initial pose');
	},

	subShellFeedback: () => {
		var shellFeedbackTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/shell_feedback',
			messageType: 'std_msgs/String'
		});
		shellFeedbackTopic.subscribe((message) => {
			switch (message.data)
			{
				case '':
					break;
				default:
					var msgs = message.data.split(/[ :]/);
					var key = msgs[0].trim();
					switch (key)
					{
						case 'dbparam':
							DATA.mapList = msgs.slice(1);
							break;
						case 'update':
							if (DATA.loading.key === 'update')
							{
								DATA.loading = false;
							}
							if (msgs[1].trim() === 'success')
							{
								ALERT.info({
									title: '软件更新',
									text: '更新成功'
								});
							}
							else
							{
								ALERT.error({
									title: '软件更新',
									text: '更新失败'
								});
							}
							break;
						default:
							break;
					}
					break;
			}
		});
	},

	subDiagnostics: () => {
		var diagnosticsTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/diagnostics_agg',
			messageType: 'diagnostic_msgs/DiagnosticArray'
		});
		diagnosticsTopic.subscribe((message) => {
			for (var i = 0; i < message.status.length; i++)
			{
				if (message.status[i].name === '/Other/ros_mode')
				{
					DATA.rosMode = message.status[i].message;
				}
			}
		});
	},

	// replaced by subMappingStatus
	subRosMode: () => {
		var rosModeTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/ros_mode',
			messageType: 'std_msgs/String'
		});
		rosModeTopic.subscribe((message) => {
			DATA.mappingStatus = message.data;
		});
	},

	subMappingStatus: () => {
		var mappingStatusTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/rosnodejs/mappingStatus',
			messageType: 'std_msgs/String'
		});
		mappingStatusTopic.subscribe((message) => {
			DATA.mappingStatus = message.data;
			if (message.data === NAV.CmdEnum.SaveMap)
			{
				setTimeout(function(){
					NAV.saveMapEdit();
				}, 5000);
			}
		});
	},

	subNavCtrlStatus: () => {
		var navCtrlStatusTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/nav_ctrl_status',
			messageType: 'yocs_msgs/NavigationControlStatus'
		});
		navCtrlStatusTopic.subscribe((message) => {
			DATA.navCtrlStatus = message;
		});
	},

	subWaypointUserSub: () => {
		var waypointUserSubTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/waypoint_user_sub',
			messageType: 'std_msgs/String'
		});
		waypointUserSubTopic.subscribe((message) => {
			var index = message.data.indexOf(':');
			var statusType = message.data.substr(0, index).trim();
			var status = message.data.substring(index+1).trim();
			switch (statusType)
			{
				case 'read_status':
					DATA.plcStatus = status;
					break;
				default:
					break;
			}
		});
	},

	// subscribe robot status from rosnodejs
	subRobotStatus: () => {
		var robotStatusTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/rosnodejs/robot_status',
			messageType: 'std_msgs/String'
		});
		robotStatusTopic.subscribe((message) => {
			var index = message.data.indexOf(':');
			var statusType = message.data.substr(0, index).trim();
			var status = message.data.substring(index+1).trim();
			switch (statusType)
			{
				case 'power':
					var powerStatus = parseFloat(status);
					if (DATA.powerStatus !== powerStatus)
					{
						DATA.powerStatus = powerStatus;
					}
					break;
				case 'charge':
					var chargeStatus = parseInt(status);
					if (DATA.chargeStatus !== chargeStatus)
					{
						DATA.chargeStatus = chargeStatus;
					}
					break;
				case 'diagnostics':
					var statusList = JSON.parse(status);
					DATA.diagnosticsMsg = statusList;
				default:
					break;
			}
		});
	},

	cmdStringTopic: () => {
		return new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/cmd_string',
			messageType: 'std_msgs/String'
		});
	},

	waypointUserPubTopic: () => {
		return new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/waypoint_user_pub',
			messageType: 'std_msgs/String'
		});
	},

	pubCmdString: (msg) => {
		var cmdStringTopic = NAV.cmdStringTopic();
		var msg = new ROSLIB.Message({
            data: msg
        });
        cmdStringTopic.publish(msg);
				console.log(msg);
	},

	pubMapEditObstacle: (obstacle) => {
		var MapEditObstacleTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/map_edit_obstacle',
			messageType: 'geometry_msgs/Polygon'
		});
		var msg = new ROSLIB.Message({
            points: obstacle
        });
		MapEditObstacleTopic.publish(msg);
	},

	// save map
	saveMap: () => {
		console.log(`[INFO][${new Date().getTime()}]pub gmapping pose`);
		NAV.pubCmdString(NAV.CmdEnum.GamppingPose);
		console.log(`[INFO][${new Date().getTime()}]pub save map`);
		NAV.pubCmdString(NAV.CmdEnum.SaveMap);
	},

	//save map_edit(default)
	saveMapEdit: () => {
		console.log(`[INFO][${new Date().getTime()}]pub load map`);
		NAV.pubCmdString(NAV.CmdEnum.LoadMap);
		setTimeout(function(){
			console.log(`[INFO][${new Date().getTime()}]pub save map edit`);
			NAV.pubCmdString(NAV.CmdEnum.SaveMapEdit);
		}, 2000);
	},

	// add waypoint
	addWaypoint: (options) => {
		if (!DATA.robotPoseMsg)
		{
			console.error('[ERROR]Failed to get robot pose');
			ALERT.error({
				title: '站点添加错误',
				text: '无法获得当前机器人位姿'
			});
			return;
		}
		var addWaypointTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/waypoint_add',
			messageType: 'yocs_msgs/Waypoint'
		});
		var msg = new ROSLIB.Message({
			header: {
				frame_id: options.frame_id
			},
			close_enough: options.close_enough,
			goal_timeout: options.goal_timeout,
			failure_mode: options.failure_mode,
			name: options.name,
			pose: options.pose
		});
		addWaypointTopic.publish(msg);
	},

	//delete waypoint
	delWaypoint: (name) => {
		var delWaypointTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/waypoint_remove',
			messageType: 'yocs_msgs/Waypoint'
		});
		var waypoint = getWaypointByName(name);
		var msg = new ROSLIB.Message(waypoint);
		delWaypointTopic.publish(msg);
	},

	// add trajectory
	// params:
	// 	1.name: name;
	// 	2.waypoints: array of selected waypoints' name
	addTrajectory: (name, waypointsName) => {
		var addTrajectoryTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/trajectory_add',
			messageType: 'yocs_msgs/Trajectory'
		});
		var waypoints = [];
		for (var i = 0; i < waypointsName.length; i++)
		{
			var selectedWpName = waypointsName[i];
			waypoints.push(getWaypointByName(selectedWpName));
		}
		var msg = new ROSLIB.Message({
			name: name,
			waypoints: waypoints
		});
		addTrajectoryTopic.publish(msg);
	},

	// delete trajectory
	delTrajectory: (name) => {
		var delTrajTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/trajectory_remove',
			messageType: 'yocs_msgs/Trajectory'
		});
		var traj = getTrajectoryByName(name);
		var msg = new ROSLIB.Message(traj);
		delTrajTopic.publish(msg);
	},

	addWaypointsForDock: () => {
		// hrg2.0 ONLY
		// add a pubsuber waypoint to adjust angle for auto-docking
		if (DATA.robotModel === 'hrg')
		{
			var msg = {
	    		name: PARAMS.AngleAdjustWaypoint,
	    		frame_id: 'pubsuber',
	    		close_enough: 0,
	    		goal_timeout: 0,
	    		failure_mode: 'charge_adjust_done',
	    		pose: PARAMS.DefaultPose
	    	};
	    	NAV.addWaypoint(msg);
		}
		for (var key in NAV.WaypointsForDock)
		{
			if (key === 'scanMarker_scan')
			{
				for (var i = 9; i > 3; i--)
				{
					var msg = {};
					msg = $.extend(msg, NAV.WaypointsForDock[key]);
					msg.name += `_${i}`;
					msg.close_enough = i * 0.1;
					NAV.addWaypoint(msg);
				}
				continue;
			}
			NAV.addWaypoint(NAV.WaypointsForDock[key]);
		}
	},

	addTrajForDock: () => {
		var timer = setInterval(function(){
			if (DATA.waypointsMsg.waypoints.length !== 0)
			{
				var waypointEnd = DATA.waypointsMsg.waypoints[DATA.waypointsMsg.waypoints.length-1];
				// check if all waypoints added
				if (waypointEnd.name === 'timer_sail')
				{
					// hrg2.0 ONLY
					// add pubsuber waypoint to adjust angle in docking begin trajectory
					if (DATA.robotModel === 'hrg')
					{
						NAV.DockingBegin.splice(3, 1, PARAMS.AngleAdjustWaypoint);
					}
					NAV.addTrajectory(NAV.DockingBeginTrajName, NAV.DockingBegin);
					if (DATA.dockInitPoseName)
					{
						// hrg2.0 ONLY
						// remove initial_pose waypoint in docking end trajectory
						if (DATA.robotModel === 'hrg')
						{
							NAV.addTrajectory(NAV.DockingEndTrajName, NAV.DockingEnd.slice(1));
						}
						else
						{
							NAV.DockingEnd[0] = DATA.dockInitPoseName;
							NAV.addTrajectory(NAV.DockingEndTrajName, NAV.DockingEnd);
						}
					}
					clearInterval(timer);
					timer = null;
					console.log('[INFO]trajectories for docking added');
				}
			}
		}, 500);
	},

	navCtrl: (name, control, ...rest) => {
		var allowInterrupt = false;
		if (rest[0] === true)
		{
			allowInterrupt = true;
		}
		if (!allowInterrupt)
		{
			if (DATA.navCtrlStatus.status !== PARAMS.NavCtrlStatus.idling)
			{
				if (control === PARAMS.NavCtrl.start)
				{
					ALERT.warn({
						title: '导航中',
						text: '正在执行其他任务，当前命令将被忽略'
					});
					return;
				}
			}
		}
		var navCtrlTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/nav_ctrl',
			messageType: 'yocs_msgs/NavigationControl'
		});
		var msg = new ROSLIB.Message({
            goal_name: name,
            control: control
		});
		console.log(`[INFO]pub ${name}, ${control}`);
		console.log(msg);
		navCtrlTopic.publish(msg);
	},

	manipulateScene: (cmd, scene) => {
		var data = cmd + ':' + scene
		NAV.pubCmdString(data);
	},

	// manual control
	manualCtrl: (vel) => {
		var linear = vel.linear || 0;
		var angular = vel.angular || 0;
		var cmdVelTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/cmd_vel',
			messageType: 'geometry_msgs/Twist'
		});
		var msg = new ROSLIB.Message({
			linear: {
				x: linear,
				y: 0,
				z: 0
			},
			angular: {
				x: 0,
				y: 0,
				z: angular
			}
		});
		if (DATA.manualCtrlTimer)
		{
			clearInterval(DATA.manualCtrlTimer);
			DATA.manualCtrlTimer = null;
		}
		if (linear === 0 && angular === 0)
		{
			cmdVelTopic.publish(msg);
			return;
		}
		DATA.manualCtrlTimer = setInterval(function(){
			cmdVelTopic.publish(msg);
		}, 200);
		console.log(msg);
	},

	subLastNetworkSetting: () => {
		var lastNetworkSettingTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/rosnodejs/last_network_setting',
			messageType: 'std_msgs/String'
		});
		lastNetworkSettingTopic.subscribe((message) => {
			DATA.lastNetworkSetting = message;
		});
	},

	setNetwork: (mode, options) => {
		var netWorkTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: '/rosnodejs/network_setting',
			messageType: 'std_msgs/String'
		});
		if (mode === PARAMS.NetworkMode.ap)
		{
			var msg = new ROSLIB.Message({
				data: ''
			});
			console.log(`[INFO]switch to ap`);
		}
		else if (mode === PARAMS.NetworkMode.wifi)
		{
			var cmd = `ssid:${options.ssid},
				password: ${options.password},
				ip: ${options.ip},
				rememberSetting: ${options.rememberSetting}`;
			var msg = new ROSLIB.Message({
				data: cmd
			});
			console.log(`[INFO]switch to wifi: ${options.ssid},
				ip: ${options.ip}, rememberSetting: ${options.rememberSetting}`);
		}
		netWorkTopic.publish(msg);
	},

	update: (mode, cmd) => {
		var topic = '';
		switch (mode)
		{
			case UpdateEvent.Type.onlineAuto:
				NAV.pubCmdString(cmd);
				return;
			case UpdateEvent.Type.offlineAuto:
				topic = '/shell_string';
				break;
			case UpdateEvent.Type.others.dbparam:
				topic = '/system_shell/shell_string';
				break;
			case UpdateEvent.Type.others.openssh:
				topic = '/shell_string';
				break;
			default:
				console.error(`[ERROR]unknown update mode: ${mode}`);
				return;
		}
		var updateTopic = new ROSLIB.Topic({
			ros: NAV.ros,
			name: topic,
			messageType: 'std_msgs/String'
		});
		var message = new ROSLIB.Message({
			data: cmd
		});
		updateTopic.publish(message);
	}
};

// get waypoint object by name
// params:
//	1.name: full name of waypoint, including the prefix
// return: waypoint, undefined if not found
function getWaypointByName(name)
{
	for (var i = 0; i < DATA.waypointsMsg.waypoints.length; i++)
	{
		var waypoint = DATA.waypointsMsg.waypoints[i];
		if (waypoint.name === name)
		{
			return waypoint;
		}
	}
}

function getTrajectoryByName(name)
{
	var traj;
	for (var i = 0; i < DATA.trajectoriesMsg.trajectories.length; i++)
	{
		traj = DATA.trajectoriesMsg.trajectories[i];
		if (traj.name === name)
		{
			break;
		}
	}
	return traj;
}
