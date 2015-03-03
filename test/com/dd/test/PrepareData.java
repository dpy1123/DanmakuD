package com.dd.test;

import java.io.FileInputStream;
import java.net.URLDecoder;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dd.danmaku.resource.bean.Category;
import com.dd.danmaku.resource.service.CategoryService;
import com.mongodb.gridfs.GridFSFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring/applicationContext.xml" })
public class PrepareData {
	@Resource
	private CategoryService categoryService;
	@Resource
	private GridFsTemplate gridFsTemplate;
	@Test
	public void prepareCatagoryData() {
		String[] firstLv = {"动画","音乐","舞蹈","游戏","娱乐","技术"};
		String[][] secondLv = { { "MAD·AMV", "新番连载", "剧场·合集" },
				{ "翻唱", "演奏", "音乐视频", "VOCALOID·UTAU" }, 
				{ "宅舞", "LIVE" }, 
				{ "游戏视频", "攻略·解说" }, 
				{ "综艺", "鬼畜" },
				{ "讲座·分享", "教程·公开课", "综合" } };
		for (int i = 0; i < firstLv.length; i++) {
			Category category = new Category(Category.ROOT, firstLv[i], 1, 0L, i+1);
			categoryService.add(category);
			String pid = category.getId();
			for (int j = 0; j < secondLv[i].length; j++) {
				Category category2 = new Category(pid, secondLv[i][j], 2, 0L, 10*(i+1)+(j+1));
				categoryService.add(category2);
			}
		}
	}
	/**
	 * 准备文件数据
	 * @throws Exception 
	 */
	@Test
	public void prepareFsFile() throws Exception {
		//往fs上传一张图，fsname为NO_PIC，用来作为默认的资源预览图
		String imgPath = "C:/Users/dd/Desktop/no_preview_pic.jpg";
		FileInputStream in = new FileInputStream(imgPath);
		
		GridFSFile gfile = gridFsTemplate.store(in, com.dd.danmaku.resource.bean.Resource.NO_PIC, "image/jpeg");
		gfile.save();
		
		in.close();
	}
}
