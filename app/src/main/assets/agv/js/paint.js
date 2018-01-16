'use strict';
var PAINT = PAINT || {
	editContainer: null,
	obstaclePoints: [],

	pointLastPos: null,
	pointGraphics: null,

	lineStartPoint: null,
	lineStartPointShape: null,

	circleCenterPoint: null,
	circleCenterPointShape: null,

	rectStartPoint: null,
	rectStartPointShape: null,

	rubberLastPos: null,
	rubberGraphics: null,

	poseStartPoint: null,
	poseStartPointShape: null,

	move: (event) => {
	},

	point: (event) => {
		var strokeSize = 2;
		var strokeColor = createjs.Graphics.getRGB(40,53,147,1);
		var graphics = new createjs.Graphics();
		graphics.setStrokeStyle(strokeSize);
    	graphics.beginStroke(strokeColor);
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    }
	    var mapPos = TF.pxToRos(pos);
	    PAINT.obstaclePoints.push({
	    	x: mapPos.x,
	    	y: mapPos.y,
	    	z: 0
	    });

    	if (!PAINT.pointLastPos)
		{
			graphics.moveTo(pos.x, pos.y);
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'mapEdit';
			DATA.stage.stage.addChild(PAINT.editContainer);
		}
	    else
	    {
	    	graphics.moveTo(PAINT.pointLastPos.x, PAINT.pointLastPos.y);
	    }
	    graphics.lineTo(pos.x, pos.y);
	    PAINT.pointLastPos = pos;
	    graphics.closePath();
	    graphics.endStroke();
	    var shape = new createjs.Shape(graphics);
	    PAINT.editContainer.addChild(shape);
	},

	line: (event) => {
		var strokeSize = 2;
		var strokeColor = createjs.Graphics.getRGB(40,53,147,1);
		// var graphics = new createjs.Graphics();
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    };
	    if (!PAINT.lineStartPoint)
	    {
	    	PAINT.lineStartPoint = pos;
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'mapEdit';
			// draw line start point
			var graphics = new createjs.Graphics();
			graphics.beginFill('#ff0000');
			graphics.drawCircle(pos.x, pos.y, 3);
			PAINT.lineStartPointShape = new createjs.Shape(graphics);
			DATA.stage.stage.addChild(PAINT.editContainer, PAINT.lineStartPointShape);
	    }
	    else
	    {
	    	var lineGraphics = new createjs.Graphics();
	    	lineGraphics.setStrokeStyle(strokeSize);
    		lineGraphics.beginStroke(strokeColor);
	    	lineGraphics.moveTo(PAINT.lineStartPoint.x, PAINT.lineStartPoint.y);
	    	lineGraphics.lineTo(pos.x, pos.y);
	    	// convert to map 
	    	var startRos = TF.pxToRos(PAINT.lineStartPoint);
	    	var endRos = TF.pxToRos(pos);
	 		var linePoints = [
	 		{
	 			x: startRos.x,
	 			y: startRos.y,
	 			z: 0
	 		}, 
	 		{
	 			x: endRos.x,
	 			y: endRos.y,
	 			z: 0
	 		}];
	    	DATA.mapEditObstacleList.push(linePoints);
	    	// remove start point
	    	DATA.stage.stage.removeChild(PAINT.lineStartPointShape);
	    	PAINT.lineStartPointShape = null;
	    	lineGraphics.endStroke();
	    	var shape = new createjs.Shape(lineGraphics);
	    	PAINT.editContainer.addChild(shape);
	    	DATA.mapEditStageList.push(PAINT.editContainer);
	    	PAINT.editContainer = null;
	    	PAINT.lineStartPoint = null;
	    }
	},

	circle: (event) => {
		var strokeSize = 2;
		var strokeColor = createjs.Graphics.getRGB(40,53,147,1);
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    }
	    if (!PAINT.circleCenterPoint)
	    {
	    	PAINT.circleCenterPoint = pos;
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'mapEdit';
			// draw center point
			var graphics = new createjs.Graphics();
			graphics.beginFill('#ff0000');
			graphics.drawCircle(pos.x, pos.y, 3);

			PAINT.circleCenterPointShape = new createjs.Shape(graphics);
			DATA.stage.stage.addChild(PAINT.editContainer, PAINT.circleCenterPointShape);
	    }
	    else
	    {
	    	var circleGraphics = new createjs.Graphics();
	    	circleGraphics.setStrokeStyle(strokeSize);
    		circleGraphics.beginStroke(strokeColor);
	    	var radius = Math.sqrt(Math.pow((pos.x-PAINT.circleCenterPoint.x),2)
	    		+ Math.pow((pos.y-PAINT.circleCenterPoint.y),2));
	    	circleGraphics.drawCircle(PAINT.circleCenterPoint.x, 
	    		PAINT.circleCenterPoint.y, radius);
	    	// convert to map
	    	var centerRos = TF.pxToRos(PAINT.circleCenterPoint);
	    	var arcRos = TF.pxToRos(pos);
	    	var radiusRos = Math.sqrt(Math.pow((arcRos.x-centerRos.x),2)
	    		+ Math.pow((arcRos.y-centerRos.y),2));
	    	var circlePoints = [{
	    		x: centerRos.x,
	    		y: centerRos.y, 
	    		z: radiusRos
	    	}];
	    	DATA.mapEditObstacleList.push(circlePoints);
	    	// remove start point
	    	DATA.stage.stage.removeChild(PAINT.circleCenterPointShape);
	    	PAINT.circleCenterPointShape = null;
	    	circleGraphics.endStroke();
	    	var shape = new createjs.Shape(circleGraphics);
	    	PAINT.editContainer.addChild(shape);
	    	DATA.mapEditStageList.push(PAINT.editContainer);
	    	PAINT.editContainer = null;
	    	PAINT.circleCenterPoint = null;
	    }
	},

	rect: (event) => {
		var strokeSize = 2;
		var strokeColor = createjs.Graphics.getRGB(40,53,147,1);
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    };
	    if (!PAINT.rectStartPoint)
	    {
	    	PAINT.rectStartPoint = pos;
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'mapEdit';
			// draw start point
			var graphics = new createjs.Graphics();
			graphics.beginFill('#ff0000');
			graphics.drawCircle(pos.x, pos.y, 3);
			PAINT.rectStartPointShape = new createjs.Shape(graphics);
			DATA.stage.stage.addChild(PAINT.editContainer, PAINT.rectStartPointShape);
	    }
	    else
	    {
	    	var rectGraphics = new createjs.Graphics();
	    	rectGraphics.setStrokeStyle(strokeSize);
    		rectGraphics.beginStroke(strokeColor);
	    	var width = pos.x - PAINT.rectStartPoint.x;
	    	var height = pos.y - PAINT.rectStartPoint.y;
	    	var rectPointsPx = [];
	    	rectPointsPx[0] = PAINT.rectStartPoint;
	    	rectPointsPx[1] = {
	    		x: PAINT.rectStartPoint.x+width,
	    		y: PAINT.rectStartPoint.y
	    	};
	    	rectPointsPx[2] = {
	    		x: PAINT.rectStartPoint.x+width,
	    		y: PAINT.rectStartPoint.y+height
	    	};
	    	rectPointsPx[3] = {
	    		x: PAINT.rectStartPoint.x,
	    		y: PAINT.rectStartPoint.y+height
	    	};
	    	rectGraphics.moveTo(rectPointsPx[0].x, rectPointsPx[0].y);
	    	rectGraphics.lineTo(rectPointsPx[1].x, rectPointsPx[1].y);
	    	rectGraphics.lineTo(rectPointsPx[2].x, rectPointsPx[2].y);
	    	rectGraphics.lineTo(rectPointsPx[3].x, rectPointsPx[3].y);
	    	rectGraphics.lineTo(rectPointsPx[0].x, rectPointsPx[0].y);  
	    	// convert to map
	    	var rectPoints = [];
	    	for (var i = 0; i < rectPointsPx.length; i++)
	    	{
	    		var p = TF.pxToRos(rectPointsPx[i]);
	    		rectPoints.push({
	    			x: p.x,
	    			y: p.y,
	    			z: 0
	    		});
	    	}
	    	DATA.mapEditObstacleList.push(rectPoints);
	    	// remove start point
	    	DATA.stage.stage.removeChild(PAINT.rectStartPointShape);
	    	PAINT.rectStartPointShape = null;
	    	rectGraphics.endStroke();
	    	var shape = new createjs.Shape(rectGraphics);
	    	PAINT.editContainer.addChild(shape);
	    	DATA.mapEditStageList.push(PAINT.editContainer);
	    	PAINT.editContainer = null;
	    	PAINT.rectStartPoint = null;
	    }
	},

	rubber: (event) => {
		var strokeSize = 5;
		var strokeColor = createjs.Graphics.getRGB(234, 234, 246, 1);
		var graphics = new createjs.Graphics();
		graphics.setStrokeStyle(strokeSize);
    	graphics.beginStroke(strokeColor);
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    }
	    var mapPos = TF.pxToRos(pos);
	    PAINT.obstaclePoints.push({
	    	x: mapPos.x,
	    	y: mapPos.y,
	    	z: -1
	    });

    	if (!PAINT.rubberLastPos)
		{
			graphics.moveTo(pos.x, pos.y);
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'mapEdit';
			DATA.stage.stage.addChild(PAINT.editContainer);
		}
	    else
	    {
	    	graphics.moveTo(PAINT.rubberLastPos.x, PAINT.rubberLastPos.y);
	    }
	    graphics.lineTo(pos.x, pos.y);
	    PAINT.rubberLastPos = pos;
	    graphics.closePath();
	    graphics.endStroke();
	    var shape = new createjs.Shape(graphics);
	    PAINT.editContainer.addChild(shape);
	},

	pose: (event) => {
		var strokeSize = 2;
		var strokeColor = '#00ff00';
		// var graphics = new createjs.Graphics();
    	var pos = {
	    	x: event.stageX,
	    	y: event.stageY
	    };
	    if (!PAINT.poseStartPoint)
	    {
	    	PAINT.poseStartPoint = pos;
			PAINT.editContainer = new createjs.Container();
			PAINT.editContainer.name = 'poseEstimate';
			// draw start point
			var graphics = new createjs.Graphics();
			graphics.beginFill('#00ff00');
			graphics.drawCircle(pos.x, pos.y, 5);
			PAINT.poseStartPointShape = new createjs.Shape(graphics);
			DATA.stage.stage.addChild(PAINT.editContainer, PAINT.poseStartPointShape);
	    }
	    else
	    {
	    	var poseGraphics = new createjs.Graphics();
	    	poseGraphics.setStrokeStyle(strokeSize);
    		poseGraphics.beginStroke(strokeColor);
	    	poseGraphics.moveTo(PAINT.poseStartPoint.x, PAINT.poseStartPoint.y);
	    	poseGraphics.lineTo(pos.x, pos.y);
	    	// convert to map 
	    	var startRos = TF.pxToRos(PAINT.poseStartPoint);
	    	var endRos = TF.pxToRos(pos);
	    	var dis = Math.sqrt(Math.pow((startRos.x-endRos.x),2)+Math.pow((startRos.y-endRos.y),2));
	    	var sin = (endRos.y - startRos.y) / dis;
	    	var cos = (endRos.x - startRos.x) / dis;
	    	var yaw = Math.acos(cos);
	    	if (sin < 0)
	    	{
	    		yaw = 2 * Math.PI - yaw;		
	    	}
	    	var pose = {
	    		position: {
	    			x: startRos.x,
	    			y: startRos.y,
	    			z: 0
	    		},
	    		orientation: TF.thetaToQuaternion(yaw)
	    	};
	    	NAV.sendInitialPose(pose);
	    	poseGraphics.endStroke();
	    	var shape = new createjs.Shape(poseGraphics);
	    	PAINT.editContainer.addChild(shape);
	    	PAINT.poseStartPoint = null;
	    	DATA.stage.stage.removeChild(shape);
	    	DATA.stage.stage.removeEventListener('click', PAINT.pose);
	    	$('#pose_estimate').css('background-color', 'transparent');	
	    	setTimeout(function(){
	    		// remove pose estimate arrow
	    		DATA.stage.stage.removeChild(PAINT.editContainer, PAINT.poseStartPointShape);
	    		PAINT.editContainer = null;
	    		PAINT.poseStartPointShape = null;
	    	}, 500);
	    }
	},

	pressupHandle: () => {
		if (PAINT.pointLastPos)
		{
			PAINT.pointLastPos = null;	
		}
		else if (PAINT.rubberLastPos)
		{
			PAINT.rubberLastPos = null;
		}
		DATA.mapEditObstacleList.push(PAINT.obstaclePoints);
		DATA.mapEditStageList.push(PAINT.editContainer);
		PAINT.obstaclePoints = [];
		PAINT.editContainer = null;	
	}
};

