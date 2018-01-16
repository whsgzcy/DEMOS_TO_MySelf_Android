'use strict';
var UpdateEvent = UpdateEvent || {
    Type: {
        onlineAuto: "online",
        offlineAuto: "offline",
        others: {
        	dbparam: "dbparam",
        	openssh: "openssh",
        	apache: "apache",
        }
    },
    UpdateString: {
        onlineAuto: "update",
        offlineAuto: `if [ -f ~/release-$ROS_DISTRO.zip ]; 
        				  then unzip ~/release-$ROS_DISTRO.zip; 
	        			  release-$ROS_DISTRO/install/share/bringup/shell/update-offline.sh; 
	        			  rm -r release-$ROS_DISTRO; 
	        			  rm ~/release-$ROS_DISTRO.zip; 
	        			  rostopic pub -1 /shell_feedback std_msgs/String "update:success"; 
        			  else  
        			  	  rostopic pub -1 /shell_feedback std_msgs/String "update:failure"; 
        			  fi;`,
        others: {
            dbparam: 'if [ -f /media/`whoami`/*/dbparam-$ROS_DISTRO.zip ]; then unzip /media/`whoami`/*/dbparam-$ROS_DISTRO.zip; cd dbparam-$ROS_DISTRO; ./install.sh; cd ..; rm -r dbparam-$ROS_DISTRO; rm ~/dbparam-$ROS_DISTRO.zip; rostopic pub -1 /shell_feedback std_msgs/String "update:success"; else  rostopic pub -1 /shell_feedback std_msgs/String "update:failure"; fi;',
        	openssh: 'if [ -f /media/`whoami`/*/openssh-$ROS_DISTRO.zip ]; then unzip /media/`whoami`/*/openssh-$ROS_DISTRO.zip; cd openssh-$ROS_DISTRO; ./install.sh; cd ..; rm -r openssh-$ROS_DISTRO; rostopic pub -1 /shell_feedback std_msgs/String "update:success"; else  rostopic pub -1 /shell_feedback std_msgs/String "update:failure"; fi;',
        	apache: 'if [ -f ~/apache-$ROS_DISTRO.zip ]; then unzip ~/apache-$ROS_DISTRO.zip; cd apache-$ROS_DISTRO; ./install.sh; cd ..; rm -r apache-$ROS_DISTRO; rm ~/apache-$ROS_DISTRO.zip; rostopic pub -1 /shell_feedback std_msgs/String "update:success"; else  rostopic pub -1 /shell_feedback std_msgs/String "update:failure"; fi;',
        }
    },
    UpdateType: null,
    UpdateStart: false,
    ConnectStart:false
}
//更新选项
function radio_change(id) {
    if (id == "radio1") {
        swal({
		    title: "在线自动更新",
		    text: "请确认导航板可以连接网络！",
			imageUrl: "../image/error_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		});
		UpdateEvent.UpdateType = UpdateEvent.Type.onlineAuto;
    }
    else if (id == "radio2") {
        swal({
		    title: "离线本地更新",
		    text: "请先把dbparam-***.zip文件放置在pkg文件夹中，然后运行update脚本！",
			imageUrl: "../image/error_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		});
		UpdateEvent.UpdateType = UpdateEvent.Type.offlineAuto;
    }
    else if (id == "radio3") {
        swal({
		    title: "U盘威尔2升级包",
		    text: "请先把dbparam-***.zip文件放置在U盘根目录下插入导航板！",
			imageUrl: "../image/error_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		})
        UpdateEvent.UpdateType = UpdateEvent.Type.others.dbparam;
    }
    else if (id == "radio4") {
        swal({
		    title: "U盘安装文件传输包",
		    text: "请先把openssh-***.zip文件放置在U盘根目录下插入导航板！",
			imageUrl: "../image/error_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		})
        UpdateEvent.UpdateType = UpdateEvent.Type.others.openssh;
    }
}

$(function (){
	$('.update_button').on('click', function(){
		if (!UpdateEvent.UpdateType)
		{
			ALERT.error({
				title: '软件更新',
				text: '请选择更新方式'
			});
			return;
		}
		DATA.loading = {
                key: 'update',
                info: '更新中'
            };
		var updateCmd = '';
		switch (UpdateEvent.UpdateType)
		{
			case UpdateEvent.Type.onlineAuto:
				updateCmd = UpdateEvent.UpdateString.onlineAuto;
				break;
			case UpdateEvent.Type.offlineAuto:
				updateCmd = UpdateEvent.UpdateString.offlineAuto;
				break;
			case UpdateEvent.Type.others.dbparam:
				updateCmd = UpdateEvent.UpdateString.others.dbparam;
				break;
			case UpdateEvent.Type.others.openssh:
				updateCmd = UpdateEvent.UpdateString.others.openssh;
				break;
			default:
				break;
		}
		NAV.update(UpdateEvent.UpdateType, updateCmd);
	});
});