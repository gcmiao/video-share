package com.lovemiao.videoshare;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/list")
public class VideoListView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FileFilter m_videoFileFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			String name = file.getName();
			return name.endsWith(".mp4") || name.endsWith(".m4v")
					|| file.isDirectory();
		}
	};
	private PrintWriter m_writer;

	public VideoListView() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		m_writer = response.getWriter();

		String videoDictionaryPath = getServletContext()
				.getRealPath("../video");
		String urlPath = getServletContext().getContextPath() + "/../video";
		String showPath = "video";

		File dictionary = new File(videoDictionaryPath);
		if (dictionary.isDirectory()) {
			m_writer.write(showPath);
			m_writer.write("<br>");
			travelFiles(dictionary, urlPath, showPath, 0);
		} else {
			request.setAttribute(Setting.KEY_ERROR_CODE, Setting.INTERNAL_ERROR);
			request.setAttribute(Setting.KEY_ERROR_REASON, "internal error");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}
	}

	private void travelFiles(File dictionary, String parentUrl,
			String parentPath, int level) {
		File[] fileList = dictionary.listFiles(m_videoFileFilter);
		if (fileList == null || fileList.length == 0)
			m_writer.println("empty video list.");
		for (File file : fileList) {
			String url = parentUrl + '/' + file.getName();
			String path = parentPath + '/' + file.getName();
			if (file.isDirectory()) {
				m_writer.write("<br>");
				writeTab(level);
				m_writer.write(path);
				m_writer.write("<br>");
				travelFiles(file, url, path, level + 1);
			} else {
				url = url.replace("'", "&#39;");
				writeTab(level);
				m_writer.print("<a href='" + url + "'>" + file.getName()
						+ "</a>");
				m_writer.println("<br>");
			}
		}
	}

	private void writeTab(int level) {
		while (level-- > 0)
			m_writer.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
