# Penjelasan project
oke disini saya ada project, yaitu oauth2-authorization-server & oauth2-authorization-client.

# oauth2-authorization-server 
selaku sebagai komponen utama di OAuth2 yang bertanggung jawab untuk 
mengelola authentication & otorisasi. 

# oauth2-authorization-client
untuk authorization client itu sendiri berupa client atau pihak yang ingin akses ke data pengguna atau resource server,
atas izin pengguna dengan bantuan komponen OAuth2 (oauth2-authorization-server).
tapi di project ini, resource-server saya gabung dengan project oauth2-authorization-client.

# Base url 
1. oauth2-authorization-client url -> http://localhost:8081/oauth2/authorization/angular-client
2. oauth2-authorization-server url -> http://localhost:10000/login
3. oauth2-authorization-server oauth2 token -> http://localhost:10000/oauth2/token
4. oauth2-authorization-server code flow -> http://localhost:10000/oauth2/authorize
5. oauth2-authorization-server check validity jwt -> http://localhost:10000/oauth2/jwks
   

# Simulasi Hight Level Design

![oauth2_authorization_server_client drawio](https://github.com/user-attachments/assets/d0785c06-867c-4452-9093-25a6ab51633c)

# Capture Process by images

1. user ingin mengakes api ke authorization client (resource access)
  `http://localhost:8081/oauth2/authorization/angular-client`dari authorization client, akan diredirect, user ke halaman login `http://localhost:10000/login`

   <img width="1458" alt="image" src="https://github.com/user-attachments/assets/f4e732d3-2ee7-484a-92fd-a231c9ea45d8" />

2. proses login oauth2 (user input username & password) yang sudah terdaftar di database
 
   <img width="1463" alt="image" src="https://github.com/user-attachments/assets/888f60d3-9b35-4c59-a2ce-a3e9e39fa0f6" />

3. dari oauth2 authorization server akan proses callback code untuk authorization client, untuk proses oauth2 token
   
    `http://localhost:8081/oauth2/code/angular-client?code=qegWqt16VjdF6r1HlsU9_o6LLrTZs6xjypx81bHRaSBSb_iYH1A7kYdBdnbuzhMQqd27Ex0AtzYEmP-nNo53tQYpi9yevfhWbyBw1jzHPYl2el8a1OMNJ9QCTVsbMnYJ&state=XvoIpYG5ewEOzyepmEIAjb0-yDZweckEHh7D1f-cF2s%3D`

  <img width="1427" alt="image" src="https://github.com/user-attachments/assets/639017bb-faf3-47a9-9cd7-78d137679f02" />

4. proses oauth2 token, untuk mendapatkan access token & refresh token
 
   code yang sudah didapatkan di put di parameter -> x-www-form-urlencoded (code)  
   <img width="1143" alt="image" src="https://github.com/user-attachments/assets/8f1bc9c2-1c5e-4483-875d-c8341b98624d" />

   seperti pada gambar dibawah ini
   
   <img width="871" alt="image" src="https://github.com/user-attachments/assets/a1f1f215-28fd-4f36-ae22-f17f2d2c8b4d" />
   
   <img width="860" alt="image" src="https://github.com/user-attachments/assets/b5e13896-e4e9-4c37-836e-f5eb5c1ab259" />

   <img width="853" alt="image" src="https://github.com/user-attachments/assets/14c690e3-263c-45be-8da7-c7f9c3db1b36" />

5. proses get data dari authorization client (resource server) penyedia data
   
   masukan access token di sisi header dengan format -> Authorization - Bearer <Token>

   <img width="850" alt="image" src="https://github.com/user-attachments/assets/2a74abc6-f069-41ac-b253-7fcc7a831ddd" />


   negative case jika token tidak dibawa di sisi header

   <img width="854" alt="image" src="https://github.com/user-attachments/assets/41382f36-1edc-4b85-a541-04ba89798faa" />

# Penjelasan berdasarkan video

 bagi teman-teman yang ingin melihat detailnya, bisa lihat di youtube milik saya di url berikut ini -> 









