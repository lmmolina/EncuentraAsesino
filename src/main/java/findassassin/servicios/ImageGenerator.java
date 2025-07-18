package findassassin.servicios;

import findassassin.modelos.Personaje;
import findassassin.repositorios.StabilityAiClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageGenerator {
    private final StabilityAiClient stabilityAiClient;
    private final String apiKey = "sk-0q2GU2KQrryWPN3OMhSoXAR6YnbvjkQYgd8oW7FZNSgcAejl";

    public ImageGenerator(StabilityAiClient stabilityAiClient) {
        this.stabilityAiClient = stabilityAiClient;
    }

    @Async
    public void generateAndSaveImage(Personaje p, String fileName) throws Exception {
        String prompt = generarPromptInvestigativo(p);
        System.out.println(prompt);

        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> promptPart = new HttpEntity<>(prompt, headers);
        HttpEntity<String> formatPart = new HttpEntity<>("jpeg", headers);

        data.add("prompt", promptPart);
        data.add("output_format", formatPart);

        byte[] imageBytes = stabilityAiClient.generateImage("Bearer " + apiKey, data);
        Files.createDirectories(Paths.get("imagen"));

        String filePath = "imagen/" + fileName + ".jpeg";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
    }

    public String generarPromptInvestigativo(Personaje p) {
        StringBuilder prompt = new StringBuilder("Portrait of a ");
        if (p.getSexo() == 'M') {
            prompt.append("man");
        } else {
            prompt.append("woman");
        }
        prompt.append(" around ").append(p.getEdad()).append(" years old, ");
        prompt.append(p.getComplexion()).append(" build, about ")
                .append(p.getAltura()).append("cm tall, weighing around ")
                .append(p.getPeso()).append("kg, ");
        prompt.append("with ").append(p.getColorPelo()).append(" hair and ")
                .append(p.getTonoPiel()).append(" skin tone, ");
        if (p.isVelloFacial()) prompt.append("facial hair, ");
        if (p.isGafas()) prompt.append("wearing glasses, ");
        prompt.append("standing in a luxurious, dimly lit vintage room, cinematic lighting, high detail, realistic style, noir mystery atmosphere. ");
        prompt.append("Character is from ").append(p.getNacionalidad()).append(". ");
        prompt.append("A suspect in a detective game called 'Find the Killer'.");

        return prompt.toString();
    }

}
