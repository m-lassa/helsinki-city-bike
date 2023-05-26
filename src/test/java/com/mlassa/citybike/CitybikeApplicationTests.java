package com.mlassa.citybike;

import com.mlassa.citybike.entity.BikeStation;
import com.mlassa.citybike.entity.BikeTrip;
import com.mlassa.citybike.repository.BikeStationRepository;
import com.mlassa.citybike.repository.BikeTripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CitybikeApplicationTests {

	@Autowired
	private BikeStationRepository bikeStationRepository;

	@Autowired
	private BikeTripRepository bikeTripRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test count of bike station")
	public void testStationCount() throws IOException {
		long expectedStationCount = Files.lines(Paths.get("src/main/resources/data/asemat.csv")).skip(1).count();

		long actualStationCount = bikeStationRepository.count();

		// test that the number of stations, excluding the header row, is in line with what is in the .csv file
		assertEquals(expectedStationCount, actualStationCount, 1);
	}

	@Test
	@DisplayName("Test existence of bike stations")
	public void testStationExistence(){
		Optional<BikeStation> stationOptional = Optional.ofNullable(bikeStationRepository.findByName("Hanasaari"));
		assertTrue(stationOptional.isPresent());
	}

	@Test
	@DisplayName("Test existence of bike trips")
	public void testTripExistence(){
		Optional<BikeTrip> tripOptional = bikeTripRepository.findById(100L);
		assertTrue(tripOptional.isPresent());
	}

	@Test
	@DisplayName("Test /stations mapping")
	public void getStationsHttpRequest() throws Exception{

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/stations"))
				.andExpect(status().isOk()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();

		ModelAndViewAssert.assertViewName(mav, "station-list");

	}

	@Test
	@DisplayName("Test /trips mapping")
	public void getTripsHttpRequest() throws Exception{

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/trips"))
				.andExpect(status().isOk()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();

		ModelAndViewAssert.assertViewName(mav, "trip-list");

	}

	@Test
	@DisplayName("Test Impagination and sorting of the Bike Station page")
	public void testStationImpaginationAndSorting() {
		int pageSize = 25;
		int pageNumber = 0;
		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
		Page<BikeStation> page = bikeStationRepository.findAll(pageRequest);
		List<BikeStation> stations = page.getContent();

		// test that page size, number and sort order are correct
		assertEquals(pageSize, page.getSize());
		assertEquals(pageNumber, page.getNumber());
		assertEquals(sort, page.getSort());

		// test that the first station in alphabetical order is correct
		assertEquals("A.I. Virtasen aukio", stations.get(0).getName());

	}

	@Test
	@DisplayName("Test Impagination of the Bike Trip page")
	public void testBikeTripImpagination() {
		int pageSize = 25;
		int pageNumber = 0;

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<BikeTrip> page = bikeTripRepository.findAll(pageRequest);

		// test that page size, number and sort order are correct
		assertEquals(pageSize, page.getSize());
		assertEquals(pageNumber, page.getNumber());

	}

}
