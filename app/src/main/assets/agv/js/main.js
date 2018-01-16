'use strict';

function getRobotModel()
{
	var url = "http://" +"192.168.0.9" + ":8808/api/model";
    $.ajax({
        type: 'get',
        url: url,
        async: true,
        success: (data) => {
            DATA.robotModel = data.model;
            console.log(`[INFO]robot model: ${data.model}`);
        },
        dataType: "json"
    });
}

function getVersion()
{
	var url = "http://" + "192.168.0.9" + ":8808/api/version";
    $.ajax({
        type: 'get',
        url: url,
        async: true,
        success: (data) => {
            DATA.softwareVersion = data.version;
            console.log(`[INFO]software version: ${data.version}`);
        },
        dataType: "json"
    });
}

function getBrowserInfo()
{
	var ua = navigator.userAgent.toLowerCase();
	var btypeInfo = (ua.match( /firefox|chrome|safari|opera/g ) || "other")[ 0 ];
	if( (ua.match( /msie|trident/g ) || [] )[ 0 ] )
    {
        btypeInfo = "msie";
    }
    var pc = "";
    var prefix = "";
    var plat = "";
    var isTocuh = ("ontouchstart" in window) || (ua.indexOf( "touch" ) !== -1) || (ua.indexOf( "mobile" ) !== -1);
    if( isTocuh )
    {
        if( ua.indexOf( "ipad" ) !== -1 )
        {
            pc = "pad";
        }
        else if( ua.indexOf( "mobile" ) !== -1 )
        {
            pc = "mobile";
        }
        else if( ua.indexOf( "android" ) !== -1 )
        {
            pc = "androidPad";
        }
        else
        {
            pc = "pc";
        }
    }
    else
    {
        pc = "pc";
    }
    switch(btypeInfo)
    {
        case "chrome":
        case "safari":
        case "mobile":
            prefix = "webkit";
            break;
        case "msie":
            prefix = "ms";
            break;
        case "firefox":
            prefix = "Moz";
            break;
        case "opera":
            prefix = "O";
            break;
        default:
            prefix = "webkit";
            break
    }
    plat = (ua.indexOf( "android" ) > 0) ? "android" : navigator.platform.toLowerCase();
    var browserInfo = {
    	version: (ua.match( /[\s\S]+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[ 1 ],
        plat: plat,
        type: btypeInfo,
        pc: pc,
        prefix: prefix,
        isMobile: (pc == "pc") ? false : true
    };
    DATA.browserInfo = browserInfo;
}

function initStage(mapInfo, FPS)
{
    var FPS = FPS || 25;
    var screenWidth = window.innerWidth || document.body.clientWidth || document.documentElement.clientWidth;
    var screenHeight = window.innerHeight || document.body.clientHeight || document.documentElement.clientHeight;
    var mapInfo = mapInfo || {
        width: screenWidth,
        height: screenHeight
    };
    var rMap = mapInfo.width / mapInfo.height;
    var width = screenWidth;
    var height = screenHeight;
    if (screenWidth > screenHeight)
    {
        width = screenHeight * rMap;
        height = screenHeight;
    }
    else
    {
        width = screenWidth;
        height = screenWidth / rMap;
    }
    var mapNavCanvas = document.createElement('canvas');
    mapNavCanvas.width = width;
    mapNavCanvas.height = height;
    $('#mapNavDiv').append(mapNavCanvas);
	var stage = new createjs.Stage(mapNavCanvas);
	DATA.register('stage', {'stage': stage, 'width': width, 'height': height}, {});
	createjs.Touch.enable(stage);
	createjs.Ticker.setFPS(FPS);
	createjs.Ticker.addEventListener('tick', function(){
        // TODO: set children index here
		stage.update();
	});
}

function checkStr(str)
{
    var myReg = /^[^@\/\'\\\"\‘\’#$%&\^\*]+$/;
    return myReg.test(str);
}

//
function isEqual(actVal, target)
{
    var val = actVal.substr(0, actVal.length-2);
    val = parseInt(val);
    return (val === target);
}

function main()
{
    // register set and get methods
    DATA.register('robotModel', null, {fnSet: UI.robotModelHandle});
    DATA.register('softwareVersion', null, {fnSet: UI.softwareVersionHandle});
    DATA.register('browserInfo', null, {fnSet: UI.browserInfoHandle});

    DATA.register('ros', null, {});
    DATA.register('topic', {}, {});

	DATA.register('mapMsg', null, {fnSet: UI.dispMap}); // ros message for map or map_edit
    DATA.register('mapStage', null, {}); // bitmap for map or map_edit
    DATA.register('mapScaleStage', null, {}); // scale for map

	DATA.register('waypointsMsg', null, {fnSet: UI.dispWaypoints});
    DATA.register('waypointsStage', null, {}); // containers added to stage

	DATA.register('trajectoriesMsg', null, {fnSet: UI.dispTrajectories});

    DATA.register('robotPoseMsg', null, {fnSet: UI.dispRobot});
    DATA.register('robotPoseStage', null, {});

    DATA.register('globalPlanMsg', null, {fnSet: UI.dispGlobalPlan});
	DATA.register('globalPlanStage', null, {});

    DATA.register('localPlanMsg', null, {fnSet: UI.dispLocalPlan});
    DATA.register('localPlanStage', null, {});

    DATA.register('footprintMsg', null, {fnSet: UI.dispFootprint});
    DATA.register('footprintStage', null, {});

    DATA.register('tfMsg', {}, {});

    DATA.register('laserScanMsg', null, {fnSet: UI.dispLaserScan});
    DATA.register('laserScanStage', null, {});

    DATA.register('display', null, {fnSet: UI.display});

    DATA.register('mapList', null, {fnSet: UI.updateMapList});

    DATA.register('rosMode', null, {fnSet: UI.rosModeHandle});
    DATA.register('mappingStatus', null, {});

    DATA.register('brushStatus', null, {fnSet: UI.brushStatusHandle});

    DATA.register('mapEditStageList', [], {});
    DATA.register('mapEditObstacleList', [], {});

    DATA.register('navCtrlStatus', null, {fnSet: UI.navCtrlStatusHandle});
    DATA.register('selectedWaypoints', [], {});

    DATA.register('dockInitPoseName', null, {});// dock initial pose name
    DATA.register('powerStatus', null, {fnSet: UI.updatePowerStatus});
    DATA.register('chargeStatus', null, {fnSet: UI.updateChargeStatus});
    DATA.register('loading', null, {fnSet: UI.loading});
    DATA.register('lastNetworkSetting', null, {fnSet: NET.lastNetworkSettingHandle});
    DATA.register('powerAlarm', null, {});
    DATA.register('plcStatus', null, {fnSet: EXT.plcStatusHandle});
    DATA.register('diagnosticsMsg', null, {fnSet: UI.diagnosticsMsgHandle})

    // initialize
    getBrowserInfo();
    getRobotModel();
    getVersion();
    initStage();
    DATA.loading = '载入中';

    // initialize ros
    var url = "ws://" + "192.168.0.9" + ":9090";
    // url = "ws://192.168.0.209:9090";
    // url = "ws://10.42.0.1:9090";
    NAV.init(url);
    DEBUG.init(url);
    NAV.getRobotStatus();

    NAV.listenTf();
    // display
    NAV.dispMapAndWps('/map');
    //NAV.dispWaypoints();
    NAV.dispTrajectories();
    NAV.dispRobot();
    NAV.dispGlobalPlan();
    NAV.dispLocalPlan();
    NAV.dispFootprint();
    NAV.dispLaserScan();
    NAV.subShellFeedback();
    NAV.subDiagnostics();
    NAV.subMappingStatus();
    NAV.pubCmdString(NAV.CmdEnum.MapSelect);
    NAV.subNavCtrlStatus();
    NAV.subRobotStatus();
    NAV.subLastNetworkSetting();
    NAV.subWaypointUserSub();
}

$(()=>{
	main();
    var disps = {
        disp_map: $('#disp-map').val(),
        disp_robotPose: $('#disp-robotPose').val(),
        disp_laserScan: $('#disp-laserScan').val(),
        disp_globalPlan: $('#disp-globalPlan').val(),
        disp_localPlan: $('#disp-localPlan').val(),
        disp_footprint: $('#disp-footprint').val(),
        //disp_waypoints: $('#disp-waypoints').val()
    }

    for (var key in disps)
    {
        var name = key.split('_')[1];
        var val = disps[key] === 'true';
        DATA.display = {
            property: name,
            value: val
        }
    }

    $('#disp-map').on('change', function(){
        DATA.display = {
            property: 'map',
            value: $('#disp-map').val() === 'true'
        };
    });

    $('#disp-robotPose').on('change', function(){
        DATA.display = {
            property: 'robotPose',
            value: $('#disp-robotPose').val() === 'true'
        };
    });

    $('#disp-footprint').on('change', function(){
        DATA.display = {
            property: 'footprint',
            value: $('#disp-footprint').val() === 'true'
        };
    });

    $('#disp-laserScan').on('change', function(){
        DATA.display = {
            property: 'laserScan',
            value: $('#disp-laserScan').val() === 'true'
        };
    });

    $('#disp-globalPlan').on('change', function(){
        DATA.display = {
            property: 'globalPlan',
            value: $('#disp-globalPlan').val() === 'true'
        };
    });

    $('#disp-localPlan').on('change', function(){
        DATA.display = {
            property: 'localPlan',
            value: $('#disp-localPlan').val() === 'true'
        };
    });

    $('#disp-waypoints').on('change', function(){
        DATA.display = {
            property: 'waypoints',
            value: $('#disp-waypoints').val() === 'true'
        };
    });

    // current map click handle
    $('#currentMapName').on('click', function(){
        var mapnav = $('.map_nav');
        if (DATA.rosMode === NAV.RosMode.Navigation)
        {
            // unfold map list
            if (isEqual(mapnav.css('height'), 35))
            {
                var height = (DATA.mapList.length + 1) * 35;
                mapnav.css('height', height);
            }
            else
            {
                mapnav.css('height','35px');
            }
        }
        else if (DATA.rosMode === NAV.RosMode.Gmapping)
        {
        	clickFeedback($('#currentMapName'));
            if (DATA.mappingStatus === NAV.CmdEnum.Gmapping)
            {
                // click to save map
                console.log('[INFO]Save map');
                NAV.saveMap();
                $('#currentMapName').text('保存中');
                // debug
                DATA.loading = '保存中';
            }
            else if (DATA.mappingStatus === NAV.CmdEnum.SaveMap)
            {

            }
            else if (DATA.mappingStatus === NAV.CmdEnum.SaveMapEdit)
            {
                // move to confirm button
            }
            else
            {

            }
        }
    });

    // TODO: unsupport on mobile phone
    $('.map_nav').mouseleave(function(){
        var mapnav = $('.map_nav');
        mapnav.css('height','35px');
    });

    // fold or unfold waypoint list in side bar
    $('#sidebar_site_span').on('click', function(){
    	clickFeedback($('#sidebar_site_span'));
        if (!DATA.waypointsMsg)
        {
            ALERT.error({
                title: '站点错误',
                text: '站点不可用'
            });
            return;
        }
        var waypointListDom = $('.sidebar_site');
        var height = waypointListDom.css('height');
        if (isEqual(height, 60))// unfold
        {
            height = (DATA.waypointsMsg.waypoints.length + 2) * 60;
            waypointListDom.css('height', height);
            $('#sidebar_site_span').text('站点 ↑');
        }
        else
        {
            waypointListDom.css('height', '60px');
            $('#sidebar_site_span').text('站点 ↓');
        }
    });

    // fold or un fold trajectory list in side bar
    $('#sidebar_route_span').on('click', function(){
    	clickFeedback($('#sidebar_route_span'));
        if (!DATA.trajectoriesMsg)
        {
            ALERT.error({
                title: '轨迹错误',
                text: '轨迹不可用'
            });
            return;
        }
        var sidebarRoute = $('.sidebar_route');
        var sidebarRouteSpan = $('#sidebar_route_span');
        if (isEqual(sidebarRoute.css('height'), 60))
        {
            var height = (DATA.trajectoriesMsg.trajectories.length + 2) * 60;
            sidebarRoute.css('height', height);
            sidebarRouteSpan.text('轨迹 ↑');
        }
        else
        {
            sidebarRoute.css('height',60);
            sidebarRouteSpan.text('轨迹 ↓');
        }
    });

    // start mapping
    $('#startMapping').on('click', function(){
    	clickFeedback($('#startMapping'));
        if (DATA.rosMode === NAV.CmdEnum.Navigation)
        {
            var input = $('#mapping_div_input').val();
            if (input === DATA.mapList[0])
            {
                console.log('[INFO]Start mapping');
                NAV.pubCmdString(NAV.CmdEnum.Gmapping);
                DATA.loading = '切换中';
            }
            else
            {
                if (!checkStr(input))
                {
                    ALERT.warn({
                        title: '参数错误',
                        text: '无效的地图名称'
                    });
                    return;
                }
                NAV.manipulateScene(NAV.CmdEnum.MapInsert, input);
            }
        }
    });

    $('#zoomIn').on('click', function(){
        DATA.brushStatus = 'zoomIn';
    });

    $('#zoomOut').on('click', function(){
        DATA.brushStatus = 'zoomOut';
    });

    $('#move').on('click', function(){
        DATA.brushStatus = 'move';
    });

    $("#pencilTool").on('click', function(){
        DATA.brushStatus = 'pencilTool';
    });

    $('#point').on('click', function(){
        DATA.brushStatus = 'point';
    });

    $('#line').on('click', function(){
        DATA.brushStatus = 'line';
    });

    $('#circle').on('click', function(){
        DATA.brushStatus = 'circle';
    });

    $('#rect').on('click', function(){
        DATA.brushStatus = 'rect';
    });

    $('#rubber').on('click', function(){
        DATA.brushStatus = 'rubber';
    });

    $('#undo').on('click', function(){
        DATA.brushStatus = 'undo';
    });

    $('#redo').on('click', function(){
        DATA.brushStatus = 'redo';
    });

    $('#confirm').on('click', function(){
        DATA.brushStatus = 'confirm';
    });

    $('#pose_estimate').on('click', function(){
        UI.poseEstimate();
    });

    // add waypoint page
    $('#wpType').on('change', function(){
        var type = $('#wpType').val();
        var modeHtml = `<option value="LOOP">多次</option>
                        <option value="NONE">单次</option>`;
        var pubsuberStyle = {
            height: 0,
            overflow: 'hidden'
        };
        if (type === 'sound_play')
        {
            modeHtml = `<option value="START">播放</option>
                        <option value="STOP">停止</option>
                        <option value="ONCE">播放一次</option>`;
        }
        else if (type === 'goto') // frame_id == looper
        {
            modeHtml = '';
            for (var i = 0; i < DATA.waypointsMsg.waypoints.length; i++)
            {
                var waypoint = DATA.waypointsMsg.waypoints[i];
                var name = waypoint.name.split('_').slice(1).join('_');
                if (!name)
                {
                    name = waypoint.name;
                }
                modeHtml += `<option value="${waypoint.name}">${name}</option>`;
            }
        }
        else if (type === 'pubsuber')
        {
            pubsuberStyle = {
                height: 75,
                overflow: 'none'
            };
        }
        $('#wpMode').children().remove();
        $('#wpMode').append(modeHtml);
        $('#pubsuber_expect_li').css('height', pubsuberStyle.height);
        $('#pubsuber_expect_li').css('overflow', pubsuberStyle.overflow);
    });

    // add waypoints submit
    $('#wp_add_submit').on('click', function(){
        // waypoint type
        var rawFrameId = $('#wpType').val();
        if (rawFrameId === '')
        {
            ALERT.error({
                title: '参数错误',
                text: '请选择站点类型'
            });
            return;
        }
        var frame_id;
        if (rawFrameId === 'goto')
        {
            frame_id = 'looper';
        }
        else if (rawFrameId === 'dock')
        {
            // TODO: add dock type in waypoint_navi
            frame_id = 'initial_pose';
        }
        else
        {
            frame_id = rawFrameId;
        }
        // check if number
        var close_enough = $('#close_enough').val();
        close_enough = parseFloat(close_enough);
        var goal_timeout = $('#timeout').val();
        goal_timeout = parseFloat(goal_timeout);
        var failure_mode = $('#wpMode').val();
        if (frame_id === 'pubsuber')
        {
            failure_mode = $('#pubsuber_expect').val();
        }
        var name = $('#wpName').val();
        if (!checkStr(name))
        {
            ALERT.error({
                title: '参数错误',
                text: '站点名称不合法'
            });
            return;
        }
        name = NAV.WaypointPrefix[frame_id] + '_' + name;
        var waypointInfo = {
            frame_id: frame_id,
            close_enough: close_enough,
            goal_timeout: goal_timeout,
            failure_mode: failure_mode,
            name: name,
            pose: DATA.robotPoseMsg
        }
        NAV.addWaypoint(waypointInfo);
        console.log('[INFO]waypoint added');
        // add docking begin waypoint
        if (rawFrameId === 'dock')
        {
            DATA.dockInitPoseName = name;
            // assemble waypoint msg
            waypointInfo.frame_id = 'map';
            waypointInfo.failure_mode = 'LOOP';
            waypointInfo.name = NAV.DockingBegin[0];
            // calc docking begin waypoint pose
            var pose = TF.getDockingBeginPose(waypointInfo.pose, PARAMS.DockingBeginDis);
            waypointInfo.pose = pose;
            NAV.addWaypoint(waypointInfo);
            NAV.addWaypointsForDock();
            NAV.addTrajForDock();
            console.log('[INFO]docking begin waypoint added');
            DATA.loading = {
                key: 'addDock',
                info: '保存中'
            };
        }
    });

    // trajectories
    $('#dock').on('click', function(){
        var isCheck = $('#dock').attr('checked');
        DATA.isDock = false;
        if (isCheck === 'checked')
        {
            DATA.isDock = true;
        }
        console.log(DATA.isDock);
    });

    $('#waypointTrajAdd').on('change', function(){
        var fullName = $('#waypointTrajAdd').val();
        if (fullName === '')
        {
            ALERT.error({
                title: '参数错误',
                text: '请至少选择一个站点'
            });
        }
        DATA.selectedWaypoints.push(fullName);
        UI.dispSelectedWaypoints();
        $('#waypointTrajAdd').val('');
    });

    $('#traj_add_submit').on('click', function(){
        var name = $('#trajName').val();
        if (!checkStr(name))
        {
            ALERT.error({
                title: '参数错误',
                text: '轨迹名称不合法'
            });
            return;
        }
        if (!DATA.selectedWaypoints)
        {
            ALERT.error({
                title: '参数错误',
                text: '请选择站点'
            })
            return;
        }
        if (DATA.isDock)
        {
            name = 'dock_' + name;
        }
        NAV.addTrajectory(name, DATA.selectedWaypoints);
        $('.selectedWp').remove();
        DATA.selectedWaypoints = [];
    });

    // navigation stop button
    $('.stop').on('click', function(){
        // stop waypoint or trajectory
        clickFeedback($('.stop'));
        NAV.navCtrl('', 0);
    });

    // manual control
    // go forward
    $('.direction_box_top').on("touchstart touchend click mouseleave", function (e) {
        switch (e.type)
        {
            case 'touchstart':
            case 'click':
                e.preventDefault();
                $('.direction_box_top').css('background-image', "url(../image/top_click.png)");
                NAV.manualCtrl({
                    linear: NAV.ManualCtrlVel.linear
                });
                break;
            case 'touchend':
            case 'mouseleave':
                $('.direction_box_top').css('background-image', "url(../image/top.png)");
                NAV.manualCtrl({});
                break;
            default:
                break;
        }
    });

    // go back
    $('.direction_box_bottom').on("touchstart touchend click mouseleave", function (e) {
        switch (e.type)
        {
            case 'touchstart':
            case 'click':
                e.preventDefault();
                $('.direction_box_bottom').css('background-image', "url(../image/bottom_click.png)");
                NAV.manualCtrl({
                    linear: -NAV.ManualCtrlVel.linear
                });
                break;
            case 'touchend':
            case 'mouseleave':
                $('.direction_box_bottom').css('background-image', "url(../image/bottom.png)");
                NAV.manualCtrl({});
                break;
            default:
                break;
        }
    });

    // go left
    $('.direction_box_left').on("touchstart touchend click mouseleave", function (e) {
        switch (e.type)
        {
            case 'touchstart':
            case 'click':
                e.preventDefault();
                $('.direction_box_left').css('background-image', "url(../image/left_click.png)");
                NAV.manualCtrl({
                    angular: NAV.ManualCtrlVel.angular
                });
                break;
            case 'touchend':
            case 'mouseleave':
                $('.direction_box_left').css('background-image', "url(../image/left.png)");
                NAV.manualCtrl({});
                break;
            default:
                break;
        }
    });

    // go left
    $('.direction_box_right').on("touchstart touchend click mouseleave", function (e) {
        switch (e.type)
        {
            case 'touchstart':
            case 'click':
                e.preventDefault();
                $('.direction_box_right').css('background-image', "url(../image/right_click.png)");
                NAV.manualCtrl({
                    angular: -NAV.ManualCtrlVel.angular
                });
                break;
            case 'touchend':
            case 'mouseleave':
                $('.direction_box_right').css('background-image', "url(../image/right.png)");
                NAV.manualCtrl({});
                break;
            default:
                break;
        }
    });

    // stop
    $('.direction_box_center').on("touchstart touchend click mouseleave", function (e) {
        switch (e.type)
        {
            case 'touchstart':
            case 'click':
                e.preventDefault();
                '3b6dde'
                $('.direction_box_center').css('background', '#3b6dde');
                NAV.manualCtrl({});
                break;
            case 'touchend':
            case 'mouseleave':
                $('.direction_box_center').css('background', '#3b6dde');
                break;
            default:
                break;
        }
    });
});
