package com.bear.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InventarioApplicationTests {

	@Test
	void contextLoads() {
		assertNotNull(InventarioApplication.class);
	}

}
