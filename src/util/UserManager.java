package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    
    private static final String FILE_PATH = "data/users.txt";
    private static String currentUser = null;  // Giriş yapan kullanıcı
    
    // Kullanıcı kaydet
    public static boolean register(String username, String password) {
        // Kullanıcı zaten var mı kontrol et
        if (userExists(username)) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(username + "," + password);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Giriş yap
    public static boolean login(String username, String password) {
        Map<String, String> users = loadUsers();
        
        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            return true;
        }
        return false;
    }
    
    // Kullanıcı var mı kontrol et
    public static boolean userExists(String username) {
        Map<String, String> users = loadUsers();
        return users.containsKey(username);
    }
    
    // Dosyadan kullanıcıları yükle
    private static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        File file = new File(FILE_PATH);
        
        // Dosya yoksa boş döndür
        if (!file.exists()) {
            // data klasörünü oluştur
            file.getParentFile().mkdirs();
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    // Giriş yapan kullanıcıyı al
    public static String getCurrentUser() {
        return currentUser;
    }
    
    // Giriş yapılmış mı?
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    // Çıkış yap
    public static void logout() {
        currentUser = null;
    }
}