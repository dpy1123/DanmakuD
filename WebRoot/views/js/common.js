//解析JASON的日期内容
function json2TimeStamp(milliseconds) {
	var datetime = new Date();
	datetime.setTime(milliseconds);
	var year = datetime.getFullYear();
	//月份重0开始，所以要加1，当小于10月时，为了显示2位的月份，所以补0
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1)
			: datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime
			.getDate();
	var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime
			.getHours();
	var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes()
			: datetime.getMinutes();
	var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds()
			: datetime.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":"
			+ second;
}

/**
 * Convert seconds to MM:SS
 * @param {Integer} nb_sec A number of seconds
 * @return A time code string
 */
function parseTimeCode(nb_sec){
	nb_sec = Math.floor(nb_sec);
	var nb_min = 0;
	while(nb_sec - 60  > 0){
		nb_sec = nb_sec - 60;
		nb_min++;
	}
	var sec = nb_sec.toString();
	if(sec.length==1){
		sec = '0'+sec;
	}
	var min = nb_min.toString();
	if(min.length==1){
		min = '0'+min;
	}	
	return min+':'+sec;
};