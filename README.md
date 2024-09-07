## Stock Management System
Bu proje, Java, Maven ve Docker kullanarak geliştirilmiş bir Stok Yönetim Sistemi uygulamasıdır. Uygulama, JDBC kullanarak MySQL veritabanı ile etkileşim kurar ve ürün ekleme, listeleme, güncelleme ve silme işlemlerini gerçekleştirir.

### Proje Özellikleri
- Ürün ekleme, güncelleme, silme ve listeleme
- Düşük stok seviyesini kontrol etme
- MySQL ile Docker üzerinde çalışır
- Maven kullanılarak proje bağımlılıkları yönetilir

### Gereksinimler
- Docker (en son sürüm)
- Java 17 veya üzeri
- Maven 3.6 veya üzeri

### Kurulum Adımları
1. **Docker ile MySQL Kurulumu**

Bu proje için MySQL veritabanını Docker konteyneri üzerinde çalıştıracağız. Aşağıdaki komutları terminale yazarak MySQL sunucusunu Docker üzerinde başlatabilirsiniz:

```bash
docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=stock_management -p 3306:3306 -d mysql:latest
```
- ```--name mysql-server ```: Docker konteyneri için bir ad belirler.
- ```-e MYSQL_ROOT_PASSWORD=password```: MySQL root kullanıcısı için şifre belirler.
- ```-e MYSQL_DATABASE=stock_management```:```stock_management``` adında bir veritabanı oluşturur.
- ```-p 3306:3306```: MySQL sunucusunu 3306 portundan erişilebilir hale getirir.
- ```-d mysql:latest```: MySQL'in en son sürümünü indirip çalıştırır.

### Maven Bağımlılıklarını Kurma
Proje kök dizininde aşağıdaki komutu çalıştırarak gerekli Maven bağımlılıklarını indirin:

```mvn clean install```

### Veritabanı Yapılandırması

CREATE DATABASE IF NOT EXISTS stock_management;
USE stock_management;
```sql
CREATE TABLE IF NOT EXISTS products (
id INT AUTO_INCREMENT PRIMARY KEY,
product_name VARCHAR(100) NOT NULL,
category VARCHAR(50) NOT NULL,
price DECIMAL(10, 2) NOT NULL,
stock INT NOT NULL
);
```
### Kullanım
Program çalıştıktan sonra, aşağıdaki menü üzerinden işlemleri gerçekleştirebilirsiniz:

- Ürün Ekle
- Ürünleri Listele
- Ürün Güncelle
- Ürün Sil
- Düşük Stokları Kontrol Et
- Çıkış

Her işlem, kullanıcıdan alınan girdilere göre veritabanı üzerinde işlem yapar.

### Önemli Notlar
- Veritabanı Bağlantı Bilgileri: ```StockManagementSystem.java``` dosyasında tanımlanan ```URL```, ```USER```, ve ```PASSWORD``` değişkenlerinin doğru ayarlandığından emin olun. Varsayılan ayarlar aşağıdaki gibidir:
```java
  private static final String URL = "jdbc:mysql://localhost:3306/stock_management";
  private static final String USER = "root";
  private static final String PASSWORD = "password";
```
- Docker MySQL Başlatma: Docker konteyneri durdurulmuşsa, aşağıdaki komutla yeniden başlatabilirsiniz:
```bash
docker start mysql-server
```
- Docker MySQL Durdurma: MySQL sunucusunu durdurmak için aşağıdaki komutu kullanabilirsiniz:
```bash
docker stop mysql-server
```






