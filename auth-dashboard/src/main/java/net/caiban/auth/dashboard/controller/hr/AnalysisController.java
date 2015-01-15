package net.caiban.auth.dashboard.controller.hr;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.caiban.auth.dashboard.controller.BaseController;
import net.caiban.auth.dashboard.domain.hr.AttendanceAnalysis;
import net.caiban.auth.dashboard.domain.hr.AttendanceSchedule;
import net.caiban.auth.dashboard.dto.ExtResult;
import net.caiban.auth.dashboard.dto.PageDto;
import net.caiban.auth.dashboard.service.hr.AttendanceAnalysisService;
import net.caiban.auth.dashboard.service.hr.AttendanceScheduleService;
import net.caiban.auth.dashboard.util.DesktopConst;
import net.caiban.auth.sdk.SessionUser;
import net.caiban.utils.DateUtil;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnalysisController extends BaseController {

	@Resource
	private AttendanceAnalysisService attendanceAnalysisService;
	@Resource
	private AttendanceScheduleService attendanceScheduleService;

	@RequestMapping
	public void index(HttpServletRequest request,Map<String, Object> out) {

		List<AttendanceSchedule> list=attendanceScheduleService.queryScheduleOnly(DesktopConst.ISUSE_TRUE);
		Map<String, String> m=new HashMap<String, String>();
		for(AttendanceSchedule schedule:list){
			m.put("s"+schedule.getId(), schedule.getName());
		}
		out.put("schedule", JSONObject.fromObject(m).toString());
	}

	@RequestMapping
	public ModelAndView query(String name, String code, Date gmtTarget,
			PageDto<AttendanceAnalysis> page ,Map<String, Object> out) {
		page = attendanceAnalysisService.pageAnalysis(name, code, gmtTarget, page);
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out, 
			Date month ) throws IOException, RowsExceededException, WriteException {
		
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=Result."+System.currentTimeMillis()+".xls");
		
		String monthStr=DateUtil.toString(month, "yyyy年MM月");
		
		List<AttendanceAnalysis> list=attendanceAnalysisService.queryAnalysisByMonth(month);
		
		//提供下载
		response.setContentType("application/msexcel");
		
		OutputStream os = response.getOutputStream();
		
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		ws.addCell(new Label(0,0,"登记号码"));
		ws.addCell(new Label(1,0,"姓名"));
		ws.addCell(new Label(2,0,"系统账号"));
		ws.addCell(new Label(3,0,"统计时间"));
		ws.addCell(new Label(4,0,"应出勤"));
		ws.addCell(new Label(5,0,"实际出勤"));
		ws.addCell(new Label(6,0,"请假天数"));
		ws.addCell(new Label(7,0,"其他天数"));
		ws.addCell(new Label(8,0,"旷工天数"));
		ws.addCell(new Label(9,0,"未打卡次数"));
		ws.addCell(new Label(10,0,"迟到次数"));
		ws.addCell(new Label(11,0,"早退次数"));
		ws.addCell(new Label(12,0,"加班次数"));
		int i=1;
		for(AttendanceAnalysis analysis:list){
			if(analysis.getDayOther()==null){
				analysis.setDayOther(BigDecimal.valueOf(0));
			}
			if(analysis.getDayUnwork()==null){
				analysis.setDayUnwork(BigDecimal.valueOf(0));
			}
			ws.addCell(new Label(0,i, analysis.getCode()));
			ws.addCell(new Label(1,i, analysis.getName()));
			ws.addCell(new Label(2,i, analysis.getAccount()));
			ws.addCell(new Label(3,i, monthStr));
			ws.addCell(new Label(4,i, String.valueOf(analysis.getDayFull())));
			ws.addCell(new Label(5,i, String.valueOf(analysis.getDayActual())));
			ws.addCell(new Label(6,i, String.valueOf(analysis.getDayLeave())));
			ws.addCell(new Label(7,i, String.valueOf(analysis.getDayOther())));
			ws.addCell(new Label(8,i, String.valueOf(analysis.getDayUnwork().toString())));
			ws.addCell(new Label(9,i, String.valueOf(analysis.getDayLate())));
			ws.addCell(new Label(10,i, String.valueOf(analysis.getDayLate())));
			ws.addCell(new Label(11,i, String.valueOf(analysis.getDayEarly())));
			ws.addCell(new Label(12,i, String.valueOf(analysis.getDayOvertime())));
			i++;
		}
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容   
//		ws.addCell(labelCF);//将Label写入sheet中   
		//Label的构造函数Label(int x, int y,String aString)  xy意同读的时候的xy,aString是写入的内容.   
//		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);//设置写入字体   
//		WritableCellFormat wcfF = new WritableCellFormat(wf);//设置CellFormat   
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置,内容和格式   
		//Label的另一构造函数Label(int c, int r, String cont, CellFormat st)可以对写入内容进行格式化,设置字体及其它的属性.   
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView update(HttpServletRequest request, Map<String, Object> out, AttendanceAnalysis analysis){
		
		Integer i = attendanceAnalysisService.updateAnalysis(analysis);
		ExtResult result = new ExtResult();
		if(i!=null & i.intValue()>0){
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}
	 
	@RequestMapping
	public ModelAndView my(HttpServletRequest request, Map<String, Object> out,
			Date gmtTarget){
		
		SessionUser user=getCachedUser(request);
		out.put("name", user.getName());
		
		List<AttendanceSchedule> list=attendanceScheduleService.queryScheduleOnly(DesktopConst.ISUSE_TRUE);
		Map<String, String> m=new HashMap<String, String>();
		for(AttendanceSchedule schedule:list){
			m.put("s"+schedule.getId(), schedule.getName());
		}
		out.put("schedule", JSONObject.fromObject(m).toString());
		return null;
	}
}
