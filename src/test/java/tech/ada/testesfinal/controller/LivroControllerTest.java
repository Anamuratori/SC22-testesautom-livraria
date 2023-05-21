package tech.ada.testesfinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tech.ada.testesfinal.exception.BadRequestException;
import tech.ada.testesfinal.model.dto.LivroDTO;
import tech.ada.testesfinal.model.dto.MensagemDTO;
import tech.ada.testesfinal.model.entity.LivroEntity;
import tech.ada.testesfinal.repository.LivroRepository;
import tech.ada.testesfinal.service.LivroService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LivroControllerTest {
    @Autowired
    private LivroController controller;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LivroRepository repository;
    @MockBean
    private LivroService service;


    @Test
    public void carregouContexto() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void listarLivrosComSucesso() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void listarLivrosSemSucesso() throws Exception {
        String errorMessage = "Erro ao listar livros.";

        when(service.listar()).thenThrow(new BadRequestException(errorMessage));
        this. mockMvc.perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value(errorMessage));
    }

    @Test
    public void criarLivroComSucesso() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void criarLivroSemSucesso() throws Exception {
        String errorMessage = "Erro ao criar livro.";
        LivroDTO livroDTO = new LivroDTO();
        when(service.criar(livroDTO)).thenThrow(new BadRequestException(errorMessage));

        ResponseEntity<Object> response = controller.criar(livroDTO);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(errorMessage, ((MensagemDTO) response.getBody()).getMensagem());

    }

    @Test
    public void buscarLivroPorIdComSucesso() throws Exception {
        LivroEntity livro1 = new LivroEntity();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        repository.save(livro1);


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/livros/{id}", livro1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarLivroPorIdSemSucesso() throws Exception {
        String errorMessage = "Erro ao achar livro";
        Long livroid = 2l;

        when(service.buscarPorId(livroid)).thenThrow(new BadRequestException(errorMessage));
        this. mockMvc.perform(MockMvcRequestBuilders.get("/livros/{id}", livroid))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void criarLivroSemTitulo() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo(null);
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroSemResumo() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo(null);
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroResumoMaiorQuePermitido() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Um Defeito de Cor é uma obra magistral de Ana Maria Gonçalves." +
                "O livro conta a história de Kehinde, uma africana idosa e ex-escravizada que embarca em uma jornada" +
                "épica pelo Brasil do século XIX em busca de seu filho perdido. Com uma narrativa envolvente e" +
                "personagens vibrantes, a autora retrata a escravidão, o racismo e a luta pela liberdade," +
                "tecendo uma trama rica em emoção, resiliência e esperança. É uma obra indispensável que resgata a voz" +
                "e a história de pessoas marginalizadas pela sociedadexxxxxxxxx.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroSemPreco() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(null);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroPrecoMenorQuePermitido() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(10.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroSemPaginas() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(null);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void criarLivroPaginasMenorQuePermitido() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(10);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroSemIsbn() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn(null);
        livro1.setDataPublicacao(LocalDate.now().plusMonths(1));


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    public void criarLivroDataInvalida() throws Exception {
        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Um Defeito de Cor");
        livro1.setResumo("Testando resumo menos de 500 caracteres.");
        livro1.setSumario("1- Testando sumario1 | 2- Testando sumario2 | 3- Testando sumario 3");
        livro1.setPreco(100.0);
        livro1.setPaginas(900);
        livro1.setIsbn("9788501011756");
        livro1.setDataPublicacao(LocalDate.now().minusDays(1));


        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livro1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
