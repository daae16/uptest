package com.up;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

@WebServlet("/upload/*")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int ramSize = 100 * 1025;// 서버가 감당 가능한 메모리 사이즈
	private static int maxTotalSize = 10 * 1024 * 1024;// 최대로 올릴 수 있는 사이즈
	private static int setFileSizeMax = 2 * 1024 * 1024;// 파일 하나당 올릴 수 있는 사이즈 - 현재는 2mb
	private UploadDAO upDAO = new UploadDAOImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String contentType = request.getContentType();
//		System.out.println(contentType);

		DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
		dfiFactory.setSizeThreshold(ramSize);

		ServletFileUpload sfu = new ServletFileUpload(dfiFactory);
		sfu.setFileSizeMax(setFileSizeMax);
		sfu.setSizeMax(maxTotalSize);
		try {
			Map<String, String> upload = new HashMap<>();
			List<FileItem> fiList = sfu.parseRequest(new ServletRequestContext(request));// List로는 DB에 저장하기가 좀 힘들기 때문에
			for (FileItem fi : fiList) {
//				System.out.println(fiList+"\n↑현재 fiList에 들어가있는 파일 순서를 잘 봐");
				System.out.println(fi.isFormField());
				if (fi.isFormField()) {
					upload.put(fi.getFieldName(), fi.getString("UTF-8"));
//					System.out.println("1 : " + fi.getFieldName() + "←얘는 우리가 적은 파일 이름(String/jsp의 form에 입력한 애)이고 FormField가 맞아");
//					System.out.println("2 : " + fi.getString("UTF-8") + "←얘는 우리가 적은 파일 이름(String/브라우저에다가 입력한 애)이고 FormField가 맞아");
				} else {
//					System.out.println("3 : " + fi.getName() + "←얘는 우리가 업로드한 사진이고 FormField가 아니야");
//					System.out.println("우리는 사진을 업로드 할거니까 else(if문의 false)영역에 업로드 코드를 적어야해");
					String fileName = fi.getName();// 실제 저장되는 이름
					String extName = fileName.substring(fileName.lastIndexOf("."));
					//↑↑↑↑실제 저장되는 이름에서 .뒤에 오는 확장자를 잘라서 저장
					String path = "C:\\\\\\\\java_study_file\\\\\\\\uploads\\\\" + System.nanoTime() + extName;// 경로+나노타임시간으로저장+확장자
					File f = new File("C:\\\\java_study_file\\\\uploads\\" + fileName);
					fi.write(f);
					upload.put(fi.getFieldName(), fileName);
					upload.put(fi.getFieldName().substring(4), path);
				}
			}
			upDAO.insertUpload(upload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
//사진을 올리면 사진이름과 올린사람이름을 DB에 저장해야 하는데
//고유번호를 지정해줘서 사진에대한 식별을 할 수 있게 해준다
//파일저장이름과 원래 사용자가 올린 이름으로 두가지를 저장한다
//DB에 저장시에는 원래의 파일 경로까지 알아야 한다

//게시판을 작성
//데이터베이스 저장
//둘중 하나라도 안되면 사용자에게 말해줘야 함 

//TIP 무언가 에러가 나면 PSOT영역에 시작부분에 체크포인트 찍고 디버그돌리기
//TIP 디버깅시 원하는 변수에 드래그해서 컨+쉬프트+i하면 그 안에 들어있는 값을 볼 수 있음