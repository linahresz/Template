package TempalteClass;

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
	public static int quantidadeFotos;
	
    public static void main(String[] args) throws IOException {
        // Dados de autenticação do GitHub
        String githubToken = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIGWIqF2vsCKIBSH4Po0gTLDW/JYUK51etm/A2aHktfQY rafa.agnusdei@gmail.com";

        // Nome do proprietário do repositório e nome do repositório
        String repositoryOwner = "linahresz";
        String repositoryName = "Template";

        // Caminho do arquivo local
        String filePath = "C:\\Users\\RAFAEL\\eclipse-workspace\\Template\\Assets\\WhatsApp Image 2023-05-30 at 08.20.20.jpeg";

        // Dados para a publicação
			quantidadeFotos = 1;

        String comentario = "Este é um comentário para a publicação";

        // Cria o objeto JSON com os dados do post
        File file = new File(filePath);
        String fileName = file.getName();

        // Codifica o arquivo em Base64
        String fileContentBase64 = encodeFileToBase64(file);

        // Configura a conexão HTTP
        URL url = new URL("https://github.com/" + repositoryOwner + "/" + repositoryName + "/tree/main/" + fileName);
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
    }

    private static String encodeFileToBase64(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }
}
