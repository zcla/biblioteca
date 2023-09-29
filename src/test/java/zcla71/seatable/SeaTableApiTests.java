package zcla71.seatable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.dao.seatable.config.SeaTableBase;
import zcla71.seatable.config.SeaTableTestConfig;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.secret.SeaTableTestSecret;
import zcla71.utils.Utils;

class SeaTableApiTests {
	private SeaTableTestConfig config;
	private SeaTableTestSecret secret;
	private SeaTableApi api;

	public SeaTableApiTests() throws StreamReadException, DatabindException, IOException {
		config = Utils.getResourceAsObject(SeaTableTestConfig.class, "/config.json");
		secret = Utils.getResourceAsObject(SeaTableTestSecret.class, "/secret.json");
		api = new SeaTableApi(secret.getSeaTable().getApiToken());
	}

	@Test
	void initConfig() {
		assertNotNull(config);
		assertNotNull(config.getSeaTable());
		assertNotNull(config.getSeaTable().getBases());
		assertEquals(1, config.getSeaTable().getBases().size());
		SeaTableBase base = config.getSeaTable().getBases().iterator().next();
		assertEquals("teste", base.getBase_name());
	}
	
	@Test
	void initSecret() {
		assertNotNull(secret);
		assertNotNull(secret.getSeaTable());
		assertNotNull(secret.getSeaTable().getApiToken());
	}
	
	@Test
	void initApi() {
		assertNotNull(api);
	}

	@Test
	void getMetadata() throws IOException {
		Metadata metadata = api.getMetadata();
		assertEquals(9, metadata.getMetadata().getFormat_version());
		// assertEquals(30, metadata.getMetadata().getVersion()); // Variável. Parece ser um número de versionamento da base.
	}
}
