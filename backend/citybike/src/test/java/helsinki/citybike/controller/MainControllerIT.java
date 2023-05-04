package helsinki.citybike.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import helsinki.citybike.DatabaseImportConfig;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.repository.HSLStationRepository;
import helsinki.citybike.specifications.GridSortModel;
import helsinki.citybike.specifications.filter.BasicColumnFilter;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(DatabaseImportConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MainControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStationsGeo() throws Exception {
        this.mockMvc.perform(get("/stationsGeo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("abc1", "abc2", "abc3")))
                .andExpect(jsonPath("$[*].lon").value(containsInAnyOrder(4.0d, 1.0d, 1.0d)))
                .andExpect(jsonPath("$[*].lat").value(containsInAnyOrder(2.0d, 6.0d, 2.0d)))
                .andReturn();
    }

    @Test
    public void saveStation() throws Exception {
        StationDTO dto = StationDTO.builder()
                .externalId("005")
                .nameFi("name")
                .nameEn("name")
                .cityFi("City")
                .addressFi("addr")
                .x(7d)
                .y(6d)
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savestation")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savestation")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content, containsString("externalId must be unique"));

        result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savestation")
                        .content(asJsonString(StationDTO.builder().externalId("0067").build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        content = result.getResponse().getContentAsString();
        assertThat(content, containsString("\"nameFi\":\"must not be null\""));
        assertThat(content, containsString("\"addressFi\":\"must not be null\""));
        assertThat(content, containsString("\"x\":\"must not be null\""));
        assertThat(content, containsString("\"y\":\"must not be null\""));
        assertThat(content, containsString("\"nameEn\":\"must not be null\""));
    }

    @Test
    public void saveJourney() throws Exception {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .build();

        JourneyDTO dto = JourneyDTO.builder()
                .departureTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .departureStationId("001")
                .returnStationId("003")
                .distance(50d)
                .duration(80l)
                .build();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savejourney")
                        .content(asJsonString(mapper, dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savejourney")
                        .content(asJsonString(mapper, JourneyDTO.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content, containsString("\"duration\":\"must not be null\""));
        assertThat(content, containsString("\"departureTime\":\"must not be null\""));
        assertThat(content, containsString("\"returnStationId\":\"must not be blank\""));
        assertThat(content, containsString("\"distance\":\"must not be null\""));
        assertThat(content, containsString("\"departureStationId\":\"must not be blank\""));
        assertThat(content, containsString("\"returnTime\":\"must not be null\""));

        dto = JourneyDTO.builder()
                .departureTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .departureStationId("001")
                .returnStationId("003")
                .distance(5d)
                .duration(6l)
                .build();

        result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/savejourney")
                        .content(asJsonString(mapper, dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        content = result.getResponse().getContentAsString();
        assertThat(content, containsString("\"duration\":\"must be greater than or equal to 10\""));
        assertThat(content, containsString("\"distance\":\"must be greater than or equal to 10\""));
    }

    @Test
    public void testTop5Depart() throws Exception {
        this.mockMvc.perform(get("/top5Depart")
                        .param("stationId", "001"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].departureStationId").value(everyItem(is("001"))))
                .andExpect(jsonPath("$[*].returnStationId").value(containsInAnyOrder("002", "003")))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testTop5Ret() throws Exception {
        this.mockMvc.perform(get("/top5Ret")
                        .param("stationId", "003"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].returnStationId").value(everyItem(is("003"))))
                .andExpect(jsonPath("$[*].departureStationId").value(containsInAnyOrder("001", "002")))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testAvgDistance() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/avgDistance")
                        .param("stationId", "002"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content, containsString("Total number of journeys ending at the station: 4"));
        assertThat(content, containsString("Total number of journeys starting from the station: 2"));
        assertThat(content, containsString("The average distance of a journey ending at the station: 15.00"));
        assertThat(content, containsString("The average distance of a journey starting from the station: 20.00"));
    }

    @Test
    public void testGetStations() throws Exception {
        StationSearchCriteria searchCriteria = new StationSearchCriteria();
        GridParamsDTO<StationSearchCriteria> gridParams = new GridParamsDTO<>();
        gridParams.setSize(3);
        gridParams.setPage(0);
        gridParams.setFilterModel(searchCriteria);
        gridParams.setSortModel(new ArrayList<>());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/stations")
                        .content(asJsonString(gridParams))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.records", hasSize(3)))
                .andExpect(jsonPath("$.totalRecords", is(3)))
                .andExpect(status().isOk()).andReturn();

        BasicColumnFilter filter = new BasicColumnFilter("string", "extenralId", "003");
        searchCriteria.setExternalId(filter);
        gridParams.setSortModel(List.of(new GridSortModel("asc", "nameFi")));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/stations")
                        .content(asJsonString(gridParams))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.records", hasSize(1)))
                .andExpect(jsonPath("$.totalRecords", is(1)))
                .andExpect(jsonPath("$.records[0].nameFi", is("abc3")))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetJourneys() throws Exception {
        JourneySearchCriteria searchCriteria = new JourneySearchCriteria();
        GridParamsDTO<JourneySearchCriteria> gridParams = new GridParamsDTO<>();
        gridParams.setSize(3);
        gridParams.setPage(0);
        gridParams.setFilterModel(searchCriteria);
        gridParams.setSortModel(new ArrayList<>());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/journeys")
                        .content(asJsonString(gridParams))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.records", hasSize(3)))
                .andExpect(jsonPath("$.totalRecords", is(9)))
                .andExpect(status().isOk()).andReturn();

        BasicColumnFilter filter = new BasicColumnFilter("string", "returnStationId", "002");
        searchCriteria.setReturnStationId(filter);
        gridParams.setSortModel(List.of(new GridSortModel("desc", "distance")));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/journeys")
                        .content(asJsonString(gridParams))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.records", hasSize(3)))
                .andExpect(jsonPath("$.totalRecords", is(4)))
                .andExpect(jsonPath("$.records[0].distance", is(30d)))
                .andExpect(status().isOk()).andReturn();
    }

    public static String asJsonString(ObjectMapper mapper, final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(final Object obj) {
        return asJsonString(new ObjectMapper(), obj);
    }
}
