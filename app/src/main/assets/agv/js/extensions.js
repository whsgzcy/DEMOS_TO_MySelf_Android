'use strict';
var EXT = EXT || {
	PLC_STATUS: {
		CONVERTING: -1,
		ERROR_RUNNING: 0,
		ERROR_STOP: 1,
		RUNNING: 10,
		STOP: 11
	},

	turnOn: () => {
		console.log('[INFO]turn on plc');
		var waypointUserPubTopic = NAV.waypointUserPubTopic();
		var msg = new ROSLIB.Message({
			data: 'relay_on'
		});
		waypointUserPubTopic.publish(msg);
		setTimeout(function(){
			msg.data = 'relay_reset';
			waypointUserPubTopic.publish(msg);
		}, 1500);
	},

	turnOff: () => {
		console.log('[INFO]turn off plc');
		var waypointUserPubTopic = NAV.waypointUserPubTopic();
		var msg = new ROSLIB.Message({
			data: 'relay_off'
		});
		waypointUserPubTopic.publish(msg);
		setTimeout(function(){
			msg.data = 'relay_reset';
			waypointUserPubTopic.publish(msg);
		}, 1500);
	},

	plcStatusHandle: (rawStatus) => {
		if (rawStatus === EXT.PLC_STATUS.CONVERTING)
		{
			return;
		}
		var status = parseInt(toBinary(rawStatus).substr(14,2));
		switch (status)
		{
			case EXT.PLC_STATUS.STOP:
				$('#extensions_start').css('backgroundColor', '#3B6DDE')
									  .css('color', '#ffffff')
									  .css('border', 0);
				$('#extensions_stop').css('backgroundColor', '#3B6DDE')
									  .css('color', '#ffffff')
									  .css('border', 0);
				break;
			case EXT.PLC_STATUS.RUNNING:
				$('#extensions_start').css('backgroundColor', '#ffffff')
									  .css('color', '#3B6DDE')
									  .css('border', '1px solid #3B6DDE');
				$('#extensions_stop').css('backgroundColor', '#3B6DDE')
									  .css('color', '#ffffff')
									  .css('border', 0);
				break;
			case EXT.PLC_STATUS.ERROR_STOP:
				$('#extensions_start').css('backgroundColor', '#3B6DDE')
									  .css('color', '#ffffff')
									  .css('border', 0);
				$('#extensions_stop').css('backgroundColor', '#ff0000')
									  .css('color', '#ffffff')
									  .css('border', 0);
				break;
			case EXT.PLC_STATUS.ERROR_RUNNING:
				$('#extensions_start').css('backgroundColor', '#ff0000')
									  .css('color', '#ffffff')
									  .css('border', 0);
				$('#extensions_stop').css('backgroundColor', '#3B6DDE')
									  .css('color', '#ffffff')
									  .css('border', 0);
				break;
			default:
				break;
		}
	}
};

$(() => {
	// tailin
	$('#extensions_start').on('click', function(){
		if (DATA.plcStatus === EXT.PLC_STATUS.CONVERTING
			|| DATA.plcStatus === EXT.PLC_STATUS.RUNNING
			|| DATA.plcStatus === EXT.PLC_STATUS.ERROR_RUNNING)
		{
			return;
		}
		EXT.turnOn();
		DATA.plcStatus = EXT.PLC_STATUS.CONVERTING;
		$('#extensions_start').css('backgroundColor', '#ffffff')
							  .css('color', '#3B6DDE')
							  .css('border', '1px solid #3B6DDE');
	});

	$('#extensions_stop').on('click', function(){
		if (DATA.plcStatus === EXT.PLC_STATUS.CONVERTING
			|| DATA.plcStatus === EXT.PLC_STATUS.STOP
			|| DATA.plcStatus === EXT.PLC_STATUS.ERROR_STOP)
		{
			return;
		}
		EXT.turnOff();
		DATA.plcStatus = EXT.PLC_STATUS.CONVERTING;
		$('#extensions_stop').css('backgroundColor', '#ffffff')
							 .css('color', '#3B6DDE')
							 .css('border', '1px solid #3B6DDE');
	});
});

function toBinary(num, length)
{
    var length = length || 16;
    var binaryNum = parseInt(num).toString(2);
    var numZeros = length - binaryNum.length;
    var zeros = []
    for (var i = 0; i < numZeros; i++)
    {
        zeros.push(0);
    }
    var strZeros = zeros.join('');
    binaryNum = strZeros + binaryNum;
    return binaryNum;
}