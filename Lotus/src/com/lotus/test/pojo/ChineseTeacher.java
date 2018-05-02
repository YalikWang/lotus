package com.lotus.test.pojo;

public class ChineseTeacher implements Teacher{

	@Override
	public void teachKnowledge() {
		System.out.println("今天我们讲《琵琶行》，跟我念：“浔阳江头夜送客，枫叶荻花秋瑟瑟。”");
	}

}
