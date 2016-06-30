package com.mapbar.carlimit.controller;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mapbar.carlimit.service.CarLimitService;
import com.mapbar.carlimit.util.LimitCityEnum;

/**
 * 功能描述： 上传限行配置数据
 */
@Controller
@RequestMapping("search/")
public class UploadLimitDataController {
	private static final Logger logger = Logger.getLogger(UploadLimitDataController.class);
	@Resource
	private CarLimitService carLimitService;

	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	private static final String FILE_NAME_SUFFIX = ".properties"; // 文件后缀

	private static final String INDEX_VIEW_LOACTION = "search/upload-index";

	@RequestMapping("upload.html")
	public String upload(ModelMap map) {
		map.put("FILE_NAME_SUFFIX", FILE_NAME_SUFFIX);
		return INDEX_VIEW_LOACTION;
	}

	/**
	 * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
	 * 
	 * @param map
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadFile.html")
	public String uploadFile(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "city", required = true, defaultValue = "BEIJING") String city, HttpServletRequest request, HttpServletResponse response) {
		String filePath = "";
		try {
			// 判断文件是否为空
			if (!file.isEmpty()) {
				try {
					String filename = file.getOriginalFilename();
					if (!filename.substring(filename.lastIndexOf(".")).equals(FILE_NAME_SUFFIX)) {
						response.getWriter().write("上传文件错误,请重新上传");
						response.getWriter().flush();
						response.getWriter().close();
					}
					// 文件保存路径
					filePath = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator;
					FileUtils.deleteDirectory(new File(filePath));
					// 转存文件
					File saveFile = new File(filePath, filename);
					if (!saveFile.exists()) {
						saveFile.mkdirs();
					}
					file.transferTo(saveFile);
					// 执行解析文件
					EXECUTOR.execute(new AnalysDeal(city, filePath, saveFile));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String host = request.getHeader("host");
		System.out.println(host);
		if (StringUtils.isNotBlank(host) && !host.contains("w.mapbar.com")) {
			return "redirect:/search/list.html";
		} else {
			return "redirect:/limit/search/list.html";
		}
	}

	/***
	 * 读取上传文件中得所有文件并返回
	 * 
	 * @return
	 */
	@RequestMapping("list.html")
	public ModelAndView list(ModelMap map, HttpServletRequest request) {
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator;
		ModelAndView mav = new ModelAndView("search/list");
		File uploadDest = new File(filePath);
		String[] fileNames = uploadDest.list();
		if (fileNames.length > 0) {
			for (int i = 0; i < fileNames.length; i++) {
				// 打印出文件名
				map.put("name", fileNames[i]);
			}
			map.put("success", true);
			map.put("msg", "上传成功");
		} else {
			map.put("success", false);
			map.put("msg", "上传失败");
		}
		return mav;
	}

	private class AnalysDeal extends Thread {
		private String city;
		private String filePath;
		private File saveFile;

		public AnalysDeal(String city, String filePath, File saveFile) {
			this.city = city;
			this.filePath = filePath;
			this.saveFile = saveFile;
		}

		@Override
		public void run() {
			try {
				// 获取文件路径
				String name = LimitCityEnum.adapt(city).getDesc();
				name = name.substring(name.lastIndexOf("/") + 1);
				File dataFile = new File(filePath + name);
				saveFile.renameTo(dataFile);
				String path = this.getClass().getResource("/").getPath() + "data";
				File limitFile = new File(path, name);
				FileUtils.copyFile(dataFile, limitFile);
				long modified = limitFile.lastModified();
				carLimitService.refreshLimitDataByCity(LimitCityEnum.adapt(city), modified);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
