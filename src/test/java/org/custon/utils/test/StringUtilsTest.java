package org.custon.utils.test;

import org.custom.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testNullToEmpty(){
		// 判断对象是否为空值
		//Assert.assertNull(StringUtils.nullToEmpty(null));
		// 判断对象不为空值
		Assert.assertNotNull(StringUtils.nullToEmpty(null));
	}

}
