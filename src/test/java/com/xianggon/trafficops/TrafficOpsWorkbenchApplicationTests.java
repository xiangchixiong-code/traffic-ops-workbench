package com.xianggon.trafficops;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TrafficOpsWorkbenchApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void healthEndpointReturnsUp() throws Exception {
		mockMvc.perform(get("/api/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.status").value("UP"));
	}

	@Test
	void roadSectionsExposeSeededCodes() throws Exception {
		mockMvc.perform(get("/api/road-sections"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].code").value("NX-G6-YC-NY"));
	}

	@Test
	void deviceListContainsSeededAssets() throws Exception {
		mockMvc.perform(get("/api/devices"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].assetCode").exists());
	}

	@Test
	void exportDevicesReturnsExcelFile() throws Exception {
		mockMvc.perform(get("/api/devices/export"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	}

	@Test
	void apiDocsExposeProjectTitle() throws Exception {
		mockMvc.perform(get("/v3/api-docs"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.title").value("交通信息化工单与设备台账系统 API"));
	}

	@Test
	void importDevicesCreatesAssetFromExcel() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"devices.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				deviceImportWorkbook());

		mockMvc.perform(multipart("/api/devices/import").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.createdCount").value(1))
				.andExpect(jsonPath("$.data.skippedCount").value(0));

		mockMvc.perform(get("/api/devices").param("keyword", "CAM-TEST-900"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].assetCode").value("CAM-TEST-900"));
	}

	private byte[] deviceImportWorkbook() throws Exception {
		try (Workbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			Sheet sheet = workbook.createSheet("device-assets");
			Row header = sheet.createRow(0);
			String[] headers = {"设备名称", "资产编号", "设备类型", "状态", "路段编码", "路段名称", "区域", "安装位置", "安装日期"};
			for (int i = 0; i < headers.length; i++) {
				header.createCell(i).setCellValue(headers[i]);
			}

			Row row = sheet.createRow(1);
			row.createCell(0).setCellValue("测试抓拍摄像机");
			row.createCell(1).setCellValue("CAM-TEST-900");
			row.createCell(2).setCellValue("Camera");
			row.createCell(3).setCellValue("ONLINE");
			row.createCell(4).setCellValue("NX-G6-YC-NY");
			row.createCell(5).setCellValue("银川北收费站至永宁段");
			row.createCell(6).setCellValue("银川");
			row.createCell(7).setCellValue("测试桩号K001");
			row.createCell(8).setCellValue("2025-03-01");

			workbook.write(outputStream);
			return outputStream.toByteArray();
		}
	}

}
