<html>
	<head>
		<title>freemark</title>
	</head>	
	<body>
		<h1>展示学生信息--freemark和list整合</h1>
		<table border="1">
			<tr>
				<th>序号</th>
				<th>学号</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>地址</th>
			</tr>
			<#list mylist as stu>
				<#if stu_index == 0>
						<tr bgcolor="red;">
					<#elseif stu_index == 1>
						<tr bgcolor="blue;">
					<#elseif stu_index == 2>
						<tr bgcolor="pink">
				</#if>
				
					<td>${stu_index}</td>
					<td>${stu.id}</td>
					<td>${stu.name}</td>
					<td>${stu.age}</td>
					<td>${stu.address}</td>
				</tr>
			</#list>
		</table>
		<hr/>
		<p>${mydate?string("yyyy-MM-dd HH:mm:ss")}</p>
		<p>${mydate?date}</p>
		<p>${mydate?time}</p>
		<p>${mydate?datetime}</p>
		<hr/>
		<p>只加感叹号:${test!}</p>
		<p>加了感叹号后添加默认值：${test!"默认值"}</p>
		<p>
			<#if test??>
					test不为null
				<#else>
					test为null
			</#if>
		</p>
		<hr/>
		<#include "hello.ftl"/>
	</body>
</html>