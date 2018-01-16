'use strict';

var ICON = ICON || {
    map: mapIcon,
    timer: timerIcon,
    puber: puberIcon,
    suber: suberIcon,
    pubsuber: pubsuberIcon,
    robot: robotIcon,
    globalPlan: planShape,
    localPlan: planShape,
    footprint: footprintShape,
    laserScan: laserScanShape
};

function robotIcon(options) {
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 2;
    var strokeColor = options.strokeColor || '#3b6dde'; //e8eaf6
    var fillColor = options.fillColor || '#ffe83b';
    var fillColorExt = options.fillColorExt || createjs.Graphics.getRGB(0,150,136,0.7);
    var graphics = new createjs.Graphics();
    graphics.beginFill(fillColorExt);  
    graphics.drawCircle(size*2,size*2,size*1.5);  
    graphics.endFill();
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size/1.2);  
    graphics.endFill();
    graphics.setStrokeStyle(strokeSize);
    graphics.beginFill(fillColor);
    graphics.moveTo(size*2, size*0.6);
    graphics.lineTo(size*1.3, size*2);
    graphics.lineTo(size*2.7, size*2);
    graphics.closePath()
    graphics.endFill();
    graphics.endStroke();
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.25,
        scaleY: 0.25,
    });
}

// waypoint.map
function mapIcon(options)
{
    var size = options.size || 20;
    var strokeSize = options.strokeSize || 10;
    var strokeColor = options.strokeColor || 'rgba(0,0,0,0)';
    var fillColor = options.fillColor || '#0f89f5';
    var graphics = new createjs.Graphics();
    graphics.beginFill(fillColor);
    graphics.moveTo(size*2, -size*0.5);
    graphics.lineTo(size*1.25, size/2);
    graphics.lineTo(size*2, size*0.35);
    graphics.lineTo(size*2.75, size/2);
    graphics.beginFill(fillColor); 

    graphics.closePath();
    graphics.setStrokeStyle(strokeSize); 
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);
    graphics.drawCircle(size*2,size*2,size*1.3);
    graphics.endStroke();
    graphics.endFill();
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.2,
        scaleY: 0.2   
    });
}

// waypoint.timer
function timerIcon(options)
{
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 4;
    var strokeColor = options.strokeColor || '#ffffff';
    var fillColor = options.fillColor || '#ec6941';
    var graphics = new createjs.Graphics();
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size*1.8);  
    graphics.endFill();
    graphics.beginStroke(strokeColor); 
    graphics.setStrokeStyle(strokeSize);
    graphics.drawCircle(size*2,size*2,size);  
    graphics.endFill();
    graphics.beginStroke(strokeColor);
    graphics.setStrokeStyle(strokeSize); 
    graphics.moveTo(size*2, size*1.2);
    graphics.lineTo(size*2, size*2);
    graphics.moveTo(size*2, size*2);
    graphics.lineTo(size*1.5, size*2);
    graphics.closePath()
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.15,
        scaleY: 0.15
    });
}

// waypoint.puber
function puberIcon(options)
{
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 3;
    var strokeColor = options.strokeColor || '#ffffff';
    var fillColor = options.fillColor || '#cfa972';
    var graphics = new createjs.Graphics();
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size*1.8);  
    graphics.endFill();
    graphics.beginStroke(strokeColor); 
    graphics.setStrokeStyle(strokeSize);
    graphics.moveTo(size*2.2, size*1.5);
    graphics.lineTo(size*3, size*2);
    graphics.moveTo(size*3, size*2);
    graphics.lineTo(size, size*2);
    graphics.moveTo(size*3, size*2);
    graphics.lineTo(size*2.2, size*2.5);
    graphics.closePath()
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.2,
        scaleY: 0.2
    });
}

// waypoint.suber
function suberIcon(options)
{
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 5;
    var strokeColor = options.strokeColor || '#ffffff';
    var fillColor = options.fillColor || '#0068b7';
    var graphics = new createjs.Graphics();
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size*1.8);  
    graphics.endFill();
    graphics.setStrokeStyle(strokeSize);
    graphics.beginStroke(strokeColor); 
    graphics.moveTo(size*3, size*2);
    graphics.lineTo(size, size*2);
    graphics.closePath()
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.2,
        scaleY: 0.2
    });
}

function pubsuberIcon(options)
{
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 3;
    var strokeColor = options.strokeColor || '#ffffff';
    var fillColor = options.fillColor || '#13b5b1';
    var graphics = new createjs.Graphics();
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size*1.8);  
    graphics.endFill();
    graphics.setStrokeStyle(strokeSize);
    graphics.beginStroke(strokeColor);
    graphics.moveTo(size*2.6, size*1.6);
    graphics.lineTo(size*3.1, size*2);
    graphics.moveTo(size*3.1, size*2);
    graphics.lineTo(size*0.9, size*2);
    graphics.moveTo(size*3.1, size*2);
    graphics.lineTo(size*2.6, size*2.4);
    graphics.moveTo(size*0.9, size*2);
    graphics.lineTo(size*1.4, size*2.4);
    graphics.moveTo(size*0.9, size*2);
    graphics.lineTo(size*1.4, size*1.6);
    graphics.closePath();
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.2,
        scaleY: 0.2
    });
}

function chargeStationIcon(options)
{
    var size = options.size || 30;
    var strokeSize = options.strokeSize || 3;
    var strokeColor = options.strokeColor || '#ffffff';
    var fillColor = options.fillColor || '#ffffff';
    var graphics = new createjs.Graphics();
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);  
    graphics.drawCircle(size*2,size*2,size*1.8);  
    graphics.endFill();
    graphics.beginFill(fillColor);
    graphics.moveTo(size*2.2, size);
    graphics.lineTo(size/2, size*2);
    graphics.lineTo(size*2, size*2);
    graphics.lineTo(size*1.8, size*3);
    graphics.lineTo(size*3.6, size*2);
    graphics.lineTo(size*2, size*2);
    graphics.closePath()
    return iconWrapper(graphics, {
        regX: size * 2,
        regY: size * 2,
        rotation: 90,
        scaleX: 0.2,
        scaleY: 0.2
    });

}

function planShape(plan, options)
{
    var container = new createjs.Container();
    if (plan.poses.length < 2)
    {
        return container;
    }
    var strokeSize = options.strokeSize || 1;
    var strokeColor = options.strokeColor || createjs.Graphics.getRGB(0,255,0,1);
    var graphics = new createjs.Graphics();
    graphics.setStrokeStyle(strokeSize);
    graphics.beginStroke(strokeColor);
    var posRos = plan.poses[0].pose.position;
    var posPx = TF.rosToPx(posRos);
    graphics.moveTo(posPx.x, posPx.y);
    for (var i = 1; i < plan.poses.length; i++)
    {
        posRos = plan.poses[i].pose.position;
        posPx = TF.rosToPx(posRos);
        graphics.lineTo(posPx.x, posPx.y);
        graphics.moveTo(posPx.x, posPx.y);
    }
    graphics.endStroke();
    var shape = new createjs.Shape(graphics);
    container.addChild(shape);
    return container;
}

function footprintShape(footprint, options)
{
    var container = new createjs.Container();
    if (footprint.polygon.points.length < 3)
    {
        return container;
    }
    var strokeSize = options.strokeSize || 2;
    var strokeColor = options.strokeColor || createjs.Graphics.getRGB(0,255,0,1);
    var graphics = new createjs.Graphics();
    graphics.setStrokeStyle(strokeSize);
    graphics.beginStroke(strokeColor);
    var posRos = footprint.polygon.points[0];
    var posPx = TF.rosToPx(posRos);
    var posPxOrg = posPx;
    graphics.moveTo(posPx.x, posPx.y);
    for (var i = 1; i < footprint.polygon.points.length; i++)
    {
        posRos = footprint.polygon.points[i];
        posPx = TF.rosToPx(posRos);
        graphics.lineTo(posPx.x, posPx.y);
        graphics.moveTo(posPx.x, posPx.y);
    }
    graphics.lineTo(posPxOrg.x, posPxOrg.y);
    graphics.endStroke();
    var shape = new createjs.Shape(graphics);
    container.addChild(shape);
    return container;
}

function laserScanShape(laserScanPoints, options)
{
    var container = new createjs.Container();
    if (!laserScanPoints)
    {
        return container;
    }
    var size = options.size || 2;
    var fillColor = options.fillColor || createjs.Graphics.getRGB(244, 67, 54, 1);
    var graphics = new createjs.Graphics();
    graphics.beginFill(fillColor); 
    for (var i = 0; i < laserScanPoints.length; i++)
    {
        var posRos = laserScanPoints[i];
        var posPx = TF.rosToPx(posRos);
        graphics.moveTo(posPx.x, posPx.y);
        graphics.drawCircle(posPx.x, posPx.y, size);      
    }
    graphics.endFill();
    var shape = new createjs.Shape(graphics);
    container.addChild(shape);
    return container;
}

function tempIcon(options)
{
    var size = options.size || 12;
    var strokeSize = options.strokeSize || 1.75;
    var strokeColor = options.strokeColor || createjs.Graphics.getRGB(0,0,255,1);
    var fillColor = options.fillColor || createjs.Graphics.getRGB(0,255,0,1);
    var graphics = new createjs.Graphics();
    graphics.setStrokeStyle(strokeSize);
    graphics.moveTo(-size / 2.0, -size / 2.0);
    graphics.beginStroke(strokeColor);
    graphics.beginFill(fillColor);
    graphics.lineTo(size, 0);
    graphics.lineTo(-size / 2.0, size / 2.0);
    graphics.lineTo(size / 10.0, 0);
    graphics.closePath();
    graphics.endFill();
    graphics.endStroke();
    return iconWrapper(graphics, {});
}

function tempRobotIcon(options)
{
    var size = options.size || 40;
    var bitmap = new createjs.Bitmap('./image/map.png');
    var offset = {
        x: 0,
        y: -20
    };
    bitmap.regX = bitmap.image.width / 2 + offset.x;
    bitmap.regY = bitmap.image.height / 2 + offset.y;
    bitmap.rotation = -90;
    var container = new createjs.Container();
    container.addChild(bitmap);
    var scale = {
        x: size / bitmap.image.width,
        y: size / bitmap.image.height
    };
    container.scaleX = scale.x;
    container.scaleY = scale.y;
    DATA.stage.stage.setChildIndex(container, DATA.stage.stage.getNumChildren()-1);
    return container;
}

function tempMapIcon(options)
{
    var size = options.size || 35;
    var bitmap = new createjs.Bitmap('./image/down.png');
    var offset = {
        x: 0,
        y: 0
    };
    bitmap.regX = bitmap.image.width / 2 + offset.x;
    bitmap.regY = bitmap.image.height / 2 + offset.y;
    bitmap.rotation = -90;
    var container = new createjs.Container();
    container.addChild(bitmap);
    var scale = {
        x: size / bitmap.image.width,
        y: size / bitmap.image.height
    };
    container.scaleX = scale.x;
    container.scaleY = scale.y;
    return container;
}

// icon wrapper for graphics icon
// params:
//     1. iconGraphics: createjs graphics icon;
//     2. options:
//         regX: the left offset for the registration point
//         regY: the y offset for the registration point
//         scaleX: the factor to stretch horizontally
//         scaleY: the factor to stretch vertically
// return:
//     createjs.Container
function iconWrapper(iconGraphics, options)
{
    var size = options.size || 10;
    var reg = {
        x: options.regX || 0,
        y: options.regY || 0,
        rotation: options.rotation || 0
    };
    var scale = {
        x: options.scaleX || 1,
        y: options.scaleY || 1
    };
    var container = new createjs.Container();
    var shape = new createjs.Shape(iconGraphics);
    shape.regX = reg.x;
    shape.regY = reg.y;
    shape.rotation = reg.rotation;
    container.addChild(shape);
    container.scaleX = scale.x;
    container.scaleY = scale.y;
    return container;
}