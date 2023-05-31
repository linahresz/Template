package TempalteClass;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Postagem {
    public static void main(String[] args) throws IOException {
        // Dados de autenticação do GitHub
        String githubUsername = "linahresz";
        String githubToken = "seu_token_de_acesso";

        // Nome do repositório e caminho do arquivo no repositório
        String repositoryOwner = "linahresz";
        String repositoryName = "TemplatePub";
        String filePath = "C:\\Users\\RAFAEL\\eclipse-workspace\\Template\\Assets\\WhatsApp Image 2023-05-30 at 08.20.20.jpeg";

        // Dados do post
        String comentario = "Este é um comentário para a publicação";

        // Cria o objeto JSON com os dados do post
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        String fileName = file.getName();

        // Configura os dados do post
        PostData postData = new PostData(fileName, 1, comentario);

        // Converte o objeto em JSON
        String jsonBody = objectMapper.writeValueAsString(postData);

        // Codifica o arquivo em Base64
        String fileContentBase64 = encodeFileToBase64(file);

        // Configura a conexão HTTP
        URL url = new URL("https://api.github.com/repos/" + repositoryOwner + "/" + repositoryName + "/contents/" + fileName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "Bearer " + githubToken);
        connection.setRequestProperty("Content-Type", "application/json");

        // Cria o objeto JSON para enviar os dados ao GitHub
        String requestData = "{\"message\": \"" + comentario + "\", \"content\": \"" + fileContentBase64 + "\"}";

        // Envia o corpo da solicitação
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestData.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        // Fecha o OutputStream
        outputStream.close();

        // Lê a resposta do servidor (opcional)
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            InputStream responseStream = connection.getInputStream();
            // Processa a resposta, se necessário
            responseStream.close();
        } else {
            System.out.println("Falha ao enviar o arquivo para o GitHub. Código de resposta: " + responseCode);
        }

        // Fecha a conexão
        connection.disconnect();
    }

    private static class PostData {
        private String arquivo;
        private int quantidade;
        private String comentario;

        public PostData(String arquivo, int quantidade, String comentario) {
            this.arquivo = arquivo;
            this.quantidade = quantidade;
            this.comentario = comentario;
        }

        // Getters e Setters
    }

    private static String encodeFileToBase64(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }
}

/*import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Postagem {
    public static void main(String[] args) throws IOException {
        // URL da fachada do Middleware
        String middlewareUrl = "https://github.com/linahresz/TemplatePub.git";

        // Caminho do arquivo a ser enviado
        String filePath = "C:\\Users\\RAFAEL\\eclipse-workspace\\Template\\Assets\\WhatsApp Image 2023-05-30 at 08.20.20.jpeg";

        // Dados do post
        String comentario = "Este é um comentário para a publicação";

        // Cria o objeto JSON com os dados do post
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        String fileName = file.getName();

        // Configura os dados do post
        PostData postData = new PostData(fileName, 1, comentario);

        // Converte o objeto em JSON
        String jsonBody = objectMapper.writeValueAsString(postData);

        // Configura a conexão HTTP
        URL url = new URL(middlewareUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        // Envia o corpo da solicitação
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonBody.getBytes());
        outputStream.flush();

        // Envia o arquivo como parte da solicitação
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();

        // Fecha o OutputStream
        outputStream.close();

        // Lê a resposta do servidor (opcional)
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream responseStream = connection.getInputStream();
            // Processa a resposta, se necessário
            responseStream.close();
        } else {
            System.out.println("Falha ao enviar o arquivo. Código de resposta: " + responseCode);
        }

        // Fecha a conexão
        connection.disconnect();
    }

    private static class PostData {
        private String arquivo;
        private int quantidade;
        private String comentario;

        public PostData(String arquivo, int quantidade, String comentario) {
            this.arquivo = arquivo;
            this.quantidade = quantidade;
            this.comentario = comentario;
        }

        // Entrada de dados e Setagem

        public String getArquivo() {
            return arquivo;
        }

        public void setArquivo(String arquivo) {
            this.arquivo = arquivo;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }
    }
}*/