package org.example.service;

import com.google.genai.Client;
import io.github.cdimascio.dotenv.Dotenv;

// https://github.com/googleapis/java-genai
public class GeminiService {
    private final Client client;

    public GeminiService() {
        Dotenv dotenv = Dotenv.load();
        // https://aistudio.google.com/api-keys
        client = Client.builder()
                .apiKey(dotenv.get("GOOGLE_API_KEY"))
                .build();
    }

    public String chat(String text) {
        // https://ai.google.dev/gemini-api/docs/text-generation?hl=ko#multi-turn-conversations
        return client.chats.create("gemini-2.5-flash").sendMessage(text).text();
    }
}
