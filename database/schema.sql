DROP SCHEMA IF EXISTS `web_database`;
CREATE SCHEMA IF NOT EXISTS `web_database` DEFAULT CHARACTER SET utf8mb4;

USE `web_database`;

-- -----------------------------------------------------
-- Table `user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE = InnoDB;
INSERT INTO `user_roles`(`name`)
VALUES ('GUEST'),
       ('CLIENT'),
       ('ADMIN');

-- -----------------------------------------------------
-- Table `user_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_statuses`;
CREATE TABLE IF NOT EXISTS `user_statuses`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE = InnoDB;
INSERT INTO `user_statuses`(`name`)
VALUES ('ACTIVATED'),
       ('NOT_ACTIVATED'),
       ('BANNED');

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
# DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users`
(
    `id`               BIGINT      NOT NULL AUTO_INCREMENT,
    `login`            VARCHAR(15) NOT NULL,
    `password`         VARCHAR(32) NOT NULL,
    `first_name`       VARCHAR(15) NOT NULL,
    `last_name`        VARCHAR(15) NOT NULL,
    `telephone_number` VARCHAR(17) NOT NULL,
    `email`            VARCHAR(55) NOT NULL,
    `role_id`          BIGINT DEFAULT 1,
    `status_id`        BIGINT DEFAULT 1,
    `avatar_url`       VARCHAR(300),
    PRIMARY KEY (`id`),
    UNIQUE (`login`),
    UNIQUE (`telephone_number`),
    UNIQUE (`email`),
    FOREIGN KEY (`role_id`) REFERENCES `user_roles` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`status_id`) REFERENCES `user_statuses` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_statuses`;
CREATE TABLE IF NOT EXISTS `product_statuses`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE = InnoDB;
INSERT INTO `product_statuses`(`name`)
VALUES ('ACTIVE'),
       ('INACTIVE'),
       ('BLOCKED');

-- -----------------------------------------------------
-- Table `categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products`
(
    `id`          BIGINT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(15)    NOT NULL,
    `info`        VARCHAR(100)   NOT NULL,
    `price`       DECIMAL(10, 2) NOT NULL,
    `category_id` BIGINT DEFAULT 1,
    `status_id`   BIGINT DEFAULT 1,
    `image_url`   VARCHAR(300),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`status_id`) REFERENCES `product_statuses` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    UNIQUE (`name`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `order_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order_statuses`;
CREATE TABLE IF NOT EXISTS `order_statuses`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE = InnoDB;
INSERT INTO `order_statuses`(`name`)
VALUES ('CONFIRMED'),
       ('NOT_CONFIRMED');

-- -----------------------------------------------------
-- Table `orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders`
(
    `id`           BIGINT         NOT NULL AUTO_INCREMENT,
    `comment`      VARCHAR(100),
    `address`      VARCHAR(50),
    `time_created` long           NOT NULL,
    `total_price`  DECIMAL(12, 2) NULL,
    `user_id`      BIGINT         NOT NULL,
    `status_id`    BIGINT         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`status_id`) REFERENCES `order_statuses` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orders_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orders_products`;
CREATE TABLE IF NOT EXISTS `orders_products`
(
    `order_id`   BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    PRIMARY KEY (`order_id`, `product_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
) ENGINE = InnoDB;


# -------------------------------------
INSERT INTO categories(name)
VALUES ('others'),
       ('chains'),
       ('rings'),
       ('bracelets'),
       ('earrings');

# 46315d1d58cae3d8df137cd2ad9c4a70
INSERT INTO users(login, password, first_name, last_name, telephone_number, email, role_id, status_id)
VALUES ('ilya_user', '46315d1d58cae3d8df137cd2ad9c4a70', 'ilya', 'murin', '+375298651558', 'ilya.murin.work@gmail.com',
        2, 1),
       ('ilya_admin', '46315d1d58cae3d8df137cd2ad9c4a70', 'ilya', 'murin', '+375298651559',
        'ilia.ilia.ilia201@gmail.com', 3, 1);


INSERT INTO products(name, info, price, category_id, status_id, image_url)
VALUES ('chain_1', 'Some description of product', 243, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684758/Jewelry_Stones_Brilliant_Black_background_549625_1365x1024_gvisdf.jpg'),
       ('chain_2', 'Some description of product', 2443, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684758/3082406-beads_black_dark_design_dog-tag_fashion_identification_jewelry_necklace_pendant_reflection_table_fuzcpr.jpg'),
       ('chain_3', 'Some description of product', 2423, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684758/specimen-diamond-jewelry-necklaces-awesome-hd-desktop-wallpapers-free-downlaod-jewlery-images-high-resolution-photos-cool-images-display-1400x925-1_onlfbd.jpg'),
       ('chain_4', 'Some description of product', 243, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/jewelry-necklace-gem-gold_s6mrri.jpg'),
       ('chain_5', 'Some description of product', 24513, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/m4gwez_x7rf84.jpg'),
       ('chain_6', 'Some description of product', 2453, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/gettyimages-171588249-1024x1024_zlyvw8.jpg'),
       ('chain_7', 'Some description of product', 23, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/gettyimages-152892931-1024x1024_ed2gen.jpg'),
       ('chain_8', 'Some description of product', 24, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/20201121040528718664925_kpiajw.jpg'),
       ('chain_9', 'Some description of product', 2473, 2, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684757/istockphoto-493610261-612x612_vvzeak.jpg'),

       ('ring_1', 'Some description of product', 2843, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/celebrate-sunset-beach-creative-gemstone_dhnv0r.jpg'),
       ('ring_2', 'Some description of product', 2403, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/3106872-black-and-white_close-up_design_diamond_engagement-rings_jewelry_luxury_macro_monochrome_precious_rings_shining_wedding-bands_wedding-rings_public-domain-images_cdwua0.jpg'),
       ('ring_3', 'Some description of product', 24343, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/poul-edward-erni-w8RSCxAZA8U-unsplash_kjvltq.jpg'),
       ('ring_4', 'Some description of product', 45243, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/Jewelry-Wallpaper-58-1920x1080_fzea39.jpg'),
       ('ring_5', 'Some description of product', 2543, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/amanda-mocci-Zyp3t67rrP4-unsplash_oqhznk.jpg'),
       ('ring_6', 'Some description of product', 273, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684968/wallpapertip_bangles-wallpaper_588791_azupxq.jpg'),
       ('ring_7', 'Some description of product', 3, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684967/close-up-gold-bangles-its-reflection-glass-table-138449807_yhmdhp.jpg'),
       ('ring_8', 'Some description of product', 43, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684967/500_F_61207982_KtKG5dsvbsxHFBFyHj9csObZDmfPnCOx_yneg73.jpg'),
       ('ring_9', 'Some description of product', 43, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684967/afe8f3f60016480b8ac706bbeda9d328_u1ucuz.jpg'),
       ('ring_10', 'Some description of product', 23, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614684967/94f14fe690d62b6c5b1c05e172f57a88_oqwa8c.jpg'),
       ('ring_11', 'Some description of product', 243, 3, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685312/emerald-ring-jewelry-gem-black-feathers_smsbnx.jpg'),

       ('bracelet_1', 'Some description of product', 43, 4, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685068/tennis-bracelet-against-black-background-cordia-murphy_hko2pk.jpg'),
       ('bracelet_2', 'Some description of product', 23, 4, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685068/jewelry-diamond-bracelet-black-background-42379985_niem5x.jpg'),

       ('earring_1', 'Some description of product', 23, 5, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685601/earrings-ornaments-female-fashion_hyrysu.jpg'),
       ('earring_2', 'Some description of product', 43, 5, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685601/0204fc2b7f972d97def7fdfa37f9ea83_dzbqak.jpg'),
       ('earring_3', 'Some description of product', 2, 5, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685601/190-1905618_black-diamond-oval-halo-earrings-earrings-hd-png_xkmnxz.png'),
       ('earring_4', 'Some description of product', 43, 5, 1,
        'https://res.cloudinary.com/dkxnuhv44/image/upload/v1614685600/34-343554_diamond-jewellery-pear-shaped-drop-pearl-earrings-earring_wnruvd.png');