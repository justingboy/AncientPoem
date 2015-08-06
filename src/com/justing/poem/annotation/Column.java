/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justing.poem.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：Column.java 
 * 描述：表示列
 *
 * @author 还如�?梦中
 * @version v1.0
 * @date�?2013-7-23 上午9:47:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.FIELD })
public @interface Column {
	
	/**
	 * 列名.
	 *
	 * @return the string
	 */
	public abstract String name();

	/**
	 * 列类�?.
	 *
	 * @return the string
	 */
	public abstract String type() default "";

	/**
	 * 长度.
	 *
	 * @return the int
	 */
	public abstract int length() default 0;
	
}
