var lunarInfo=new Array( 
0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2, 
0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977, 
0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970, 
0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950, 
0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557, 
0x06ca0,0x0b550,0x15355,0x04da0,0x0a5d0,0x14573,0x052d0,0x0a9a8,0x0e950,0x06aa0, 
0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0, 
0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b5a0,0x195a6, 
0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570, 
0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0, 
0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5, 
0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930, 
0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530, 
0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45, 
0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0) 
var Animals=new Array("鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"); 
var Gan=new Array("甲","乙","丙","丁","戊","己","庚","辛","壬","癸"); 
var Zhi=new Array("子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"); 
var now = new Date(); 
var SY = now.getFullYear(); 
var SM = now.getMonth(); 
var SD = now.getDate(); 
//==== 传入 offset 传回干支, 0=甲子 
function cyclical(num) { return(Gan[num%10]+Zhi[num%12])} 

//==== 传回农历 y年的总天数 
function lYearDays(y) { 
   var i, sum = 348 
   for(i=0x8000; i>0x8; i>>=1) sum += (lunarInfo[y-1900] & i)? 1: 0 
   return(sum+leapDays(y)) 
} 

//==== 传回农历 y年闰月的天数 
function leapDays(y) { 
   if(leapMonth(y))   return((lunarInfo[y-1900] & 0x10000)? 30: 29) 
   else return(0) 
} 

//==== 传回农历 y年闰哪个月 1-12 , 没闰传回 0 
function leapMonth(y) { return(lunarInfo[y-1900] & 0xf)} 

//====================================== 传回农历 y年m月的总天数 
function monthDays(y,m) { return( (lunarInfo[y-1900] & (0x10000>>m))? 30: 29 )} 

//==== 算出农历, 传入日期物件, 传回农历日期物件 
//     该物件属性有 .year .month .day .isLeap .yearCyl .dayCyl .monCyl 
function Lunar(objDate) { 
   var i, leap=0, temp=0 
   var baseDate = new Date(1900,0,31) 
   var offset   = (objDate - baseDate)/86400000 
    this.dayCyl = offset + 40 
   this.monCyl = 14 

   for(i=1900; i<2050 && offset>0; i++) { 
       temp = lYearDays(i) 
       offset -= temp 
       this.monCyl += 12 
   } 
   if(offset<0) { 
       offset += temp; 
       i--; 
       this.monCyl -= 12 
   } 

   this.year = i 
   this.yearCyl = i-1864 

   leap = leapMonth(i) //闰哪个月 
   this.isLeap = false 

   for(i=1; i<13 && offset>0; i++) { 
       //闰月 
       if(leap>0 && i==(leap+1) && this.isLeap==false) 
         { --i; this.isLeap = true; temp = leapDays(this.year); } 
       else 
         { temp = monthDays(this.year, i); } 

       //解除闰月 
       if(this.isLeap==true && i==(leap+1)) this.isLeap = false 

       offset -= temp 
       if(this.isLeap == false) this.monCyl ++ 
   } 
   if(offset==0 && leap>0 && i==leap+1) 
       if(this.isLeap) 
         { this.isLeap = false; } 
       else 
         { this.isLeap = true; --i; --this.monCyl;} 

   if(offset<0){ offset += temp; --i; --this.monCyl; } 

   this.month = i 
   this.day = offset + 1 
} 

function YYMMDD(){ 
    
     if (now.getDay() == 0) cl = '<font color="#c00000" STYLE="font-size:9pt;">'; 
     if (now.getDay() == 6) cl = '<font color="#00c000" STYLE="font-size:9pt;">'; 
     return(SY+'年'+(SM+1)+'月'+SD+'日'); 
} 
function weekday(){ 
     var day = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六"); 
     return(day[now.getDay()]); 
} 
//==== 中文日期 
function cDay(m,d){ 
var nStr1 = new Array('日','一','二','三','四','五','六','七','八','九','十'); 
var nStr2 = new Array('初','十','廿','卅','　'); 
var s; 
if (m>10){s = '十'+nStr1[m-10]} else {s = nStr1[m]} s += '月' 
switch (d) { 
   case 10:s += '初十'; break; 
   case 20:s += '二十'; break; 
   case 30:s += '三十'; break; 
   default:s += nStr2[Math.floor(d/10)]; s += nStr1[d%10]; 
   }
   
   return(s); 
} 

function solarDay2(){ 
     var sDObj = new Date(SY,SM,SD); 
     var lDObj = new Lunar(sDObj); 
    
     //农历BB'+(cld[d].isLeap?'闰 ':' ')+cld[d].lMonth+' 月 '+cld[d].lDay+' 日 
     var tt = '农历'+cDay(lDObj.month,lDObj.day); 
     return(tt); 
} 
function solarDay3(){ 
var sTermInfo = new Array(0,21208,42467,63836,85337,107014,128867,150921,173149,

195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,

440795,462224,483532,504758) 
var solarTerm = new Array("小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至") 
var lFtv = new Array("0101*春节","0115 元宵节","0202 龙抬头节","0505 端午节","0707 七夕情人节","0715 中元节","0815 中秋节","0909 重阳节","1027 站长生日","1208 腊八节","1224 小年","0100*除夕") 
var sFtv = new Array(
		"0101*元旦",
		"0202  世界湿地日",
		"0207  国际声援南非日",
		"0210  国际气象节",
		"0214  情人节",
		"0215  中国12亿人口日",
		"0221  反对殖民制度斗争日",
		"0224  第三世界青年日",
		"0228  世界居住条件调查日",
		"0301  国际海豹日",
		"0303  全国爱耳日",
		"0305  中国青年志愿者服务日",
		"0308  妇女节",
		"0312  植树节",
		"0314  国际警察日",
		"0315  国际消费者权益日",
		"0316  手拉手情系贫困小伙伴全国统一行动日",
		"0317  国际航海日,中国国医节",
		"0318  全国科技人才活动日",
		"0321  世界林业节,消除种族歧视国际日,世界儿歌日,世界睡眠日",
		"0322  世界水日",
		"0323  世界气象日",
		"0324  世界防治结核病日",
		"0401  国际愚人节",
		"0402  国际儿童图书日",
		"0407  世界卫生日",    
	　　"0421  全国企业家活动日",   
	　　"0422  世界地球日,世界法律日",
	　　"0423  世界图书和版权日",
	　　"0424  世界青年反对殖民主义日,亚非新闻工作者日",
	　　"0425  全国预防接种宣传日",   
	　　"0426  世界知识产权日",     
	　　"0427  联谊城日",
	　　"0430  全国交通安全反思日",
		"0501  国际劳动节,国际示威游行日",
		"0503  世界哮喘日",
		"0504  中国青年节,五四运动纪念日,科技传播日",
		"0505  全国碘缺乏病防治日",
		"0508  世界红十字日,世界微笑日",
		"0512  国际护士节",
		"0515  国际家庭(咨询)日",
		"0517  世界电信日",
		"0518  国际博物馆日",
		"0520  全国母乳喂养宣传日,中国学生营养日",
		"0526  世界向人体条件挑战日",
		"0530  五卅反对帝国主义运动纪念日 ",
		"0531  世界无烟日",
		"0601  国际儿童节",
		"0605  世界环境日",
	　　"0606   全国爱眼日 ",
	　　"0611   中国人口日",
	　　"0617   世界防止荒漠化和干旱日",
	　　"0620   世界难民日",
	　　"0622   中国儿童慈善活动日",
	　　"0623   国际奥林匹克日,世界手球日",
	　　"0625   全国土地日",
	　　"0626   国际禁毒日(国际反毒品日),国际宪章日(联合国宪章日)",
	　　"0630   世界青年联欢节",
		"0701  建党节,香港回归纪念,国际建筑日,亚洲'三十亿人口日'", 
		"0702   国际体育记者日",
		"0707   中国人民抗日战争纪念日",
		"0711   世界(50亿)人口日",
		"0726   世界语(言)创立日",
		"0728   第一次世界大战爆发",
		"0730   非洲妇女日",
		"0801  建军节",
		"0806  国际电影节",
		"0808  中国男子节(父亲节)",
		"0815  日本正式宣布无条件投降日",
		"0826  全国律师咨询日",
		"0902  第二次世界大战结束日",
	　	"0903  中国抗日战争胜利纪念日",
	　　"0908  国际新闻工作者(团结)日,世界扫盲日",
	　　"0910  中国教师节",
	　　"0914  世界清洁地球日",
	　　"0916  国际臭氧层保护日",
	　　"0918  九·一八事变纪念日,第二次世界大战爆发",
	　　"0920  全国爱牙日",
	　　"0921  国际和平日  ",
	　　"0927  世界旅游日",
		"0928  孔子诞辰",
		"1001  国庆节,国际音乐日,国际老人节", 
		"1002  国际和平(与民主自由)斗争日",
		"1004  世界动物日",
		"1006  老人节",
		"1008  全国高血压日,世界视觉日,国际左撇子日",
		"1009  世界邮政日(万国邮联日)",
		"1010  辛亥革命纪念日,世界居室卫生日,世界精神卫生日", 
		"1011  声援南非政治犯日",
		"1013  中国少年先锋队诞辰日,世界保健日,国际教师节,采用格林威治时间为国际标准时间日",
		"1014   世界标准日",
		"1015   有乐窝生日",
		"1016   世界粮食日",
		"1017   世界消除贫困日",
		"1022   世界传统医药日",
		"1024   联合国日",
		"1028   世界男性健康日",
		"1031   世界勤俭日",
		"1108   中国记者节 ",
		"1109   中国消防宣传日",
		"1110   世界青年节",
		"1111   第一次世界大战结束日",
		"1112   孙中山诞辰",
		"1114   世界糖尿病日",
		"1117   国际大学生节(国际学生日)",
		"1121   世界电视日",
		"1201   世界艾滋病日 ",
	　　"1202    废除一切形式奴役世界日",
	　　"1203    世界残疾人日",
	　　"1204    中国法制宣传日 ",
	　　"1205 (经济和社会发展)国际志愿人员日,世界弱能人士日",
	　　"1207     国际民航日",
	　　"1209    一二·九运动纪念日,世界足球日",
	　　"1210    世界人权日",
	　　"1211    世界防治哮喘日",
	　　"1212    西安事变纪念日 ",
	　　"1213    南京大屠杀纪念日",
	　　"1215    世界强化免疫日",
		"1220   澳门回归纪念",
		"1221   国际篮球日",
		"1224  平安夜",
		"1225   圣诞节",
		"1226   毛主席诞辰",
		"1229   国际生物多样性日")
   var sDObj = new Date(SY,SM,SD); 
   var lDObj = new Lunar(sDObj); 
   var lDPOS = new Array(3) 
   var festival='',solarTerms='',solarFestival='',lunarFestival='',tmp1,tmp2; 
   //农历节日 
   for(i in lFtv) 
   if(lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) { 
   tmp1=Number(RegExp.$1)-lDObj.month 
   tmp2=Number(RegExp.$2)-lDObj.day 
   if(tmp1==0 && tmp2==0) lunarFestival=RegExp.$4 
   } 
   //国历节日 
   for(i in sFtv) 
   if(sFtv[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/)){ 
   tmp1=Number(RegExp.$1)-(SM+1) 
   tmp2=Number(RegExp.$2)-SD 
   if(tmp1==0 && tmp2==0) solarFestival = RegExp.$4 
   } 
   //节气 
   tmp1 = new Date((31556925974.7*(SY-1900)+sTermInfo[SM*2+1]*60000)+Date.UTC(1900,0,6,2,5)) 
   tmp2 = tmp1.getUTCDate() 
   if (tmp2==SD) solarTerms = solarTerm[SM*2+1]   
   tmp1 = new Date((31556925974.7*(SY-1900)+sTermInfo[SM*2]*60000)+Date.UTC(1900,0,6,2,5)) 
   tmp2= tmp1.getUTCDate() 
   if (tmp2==SD) solarTerms = solarTerm[SM*2] 

   if(solarTerms == '' && solarFestival == '' && lunarFestival == '') 
     festival = ''; 
   else 
     festival = solarTerms + ' ' + solarFestival + ' ' + lunarFestival; 
   return(festival); 
} 
function setCalendar(){ 

     document.write(YYMMDD()+' '+weekday()+' '+solarDay2()+' '+solarDay3()); 

} 