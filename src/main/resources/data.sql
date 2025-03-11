INSERT INTO categories (name, description, created_at)
VALUES
    ('Electronics', 'Devices, gadgets, and technology products', CURRENT_TIMESTAMP),
    ('Clothing', 'Fashionable wear for all seasons', CURRENT_TIMESTAMP),
    ('Books', 'Literature, novels, and educational material', CURRENT_TIMESTAMP),
    ('Home Appliances', 'Appliances for everyday home use', CURRENT_TIMESTAMP),
    ('Toys', 'Toys and games for children', CURRENT_TIMESTAMP),
    ('Sports Equipment', 'Gear and accessories for sports activities', CURRENT_TIMESTAMP),
    ('Furniture', 'Indoor and outdoor furniture for home and office', CURRENT_TIMESTAMP),
    ('Beauty & Personal Care', 'Products for personal hygiene and cosmetics', CURRENT_TIMESTAMP),
    ('Food & Beverages', 'Groceries, snacks, and beverages', CURRENT_TIMESTAMP),
    ('Health & Wellness', 'Supplements, fitness products, and health-related items', CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;


INSERT INTO products (name, description, price, stock_quantity, category_id, created_at) VALUES
('Smartphone Samsung Galaxy S23', 'Smartphone com display de 6.1" Full HD+, processador Snapdragon 8 Gen 2, 128GB de armazenamento', 4999.00, 50, 1, CURRENT_TIMESTAMP),
('Apple MacBook Pro 13"', 'MacBook Pro de 13 polegadas, chip M2, 256GB SSD, 8GB RAM', 12999.00, 25, 2, CURRENT_TIMESTAMP),
('Cafeteira Nespresso Essenza Mini', 'Máquina de café expresso compacta, com 19 bares de pressão', 499.00, 100, 3, CURRENT_TIMESTAMP),
('Fone de Ouvido Sony WH-1000XM5', 'Fone de ouvido bluetooth com cancelamento de ruído ativo, bateria de até 30 horas', 2499.00, 35, 4, CURRENT_TIMESTAMP),
('TV Samsung 50" 4K UHD', 'TV LED de 50 polegadas com resolução 4K UHD e suporte a HDR', 3199.00, 40, 5, CURRENT_TIMESTAMP),
('Tênis Nike Air Max 270', 'Tênis Nike Air Max 270, modelo feminino, tamanho 38', 749.00, 60, 6, CURRENT_TIMESTAMP),
('Relógio Garmin Forerunner 945', 'Relógio esportivo com GPS, monitoramento de saúde e conectividade com smartphone', 2299.00, 30, 7, CURRENT_TIMESTAMP),
('Cadeira Gamer DXRacer Racing', 'Cadeira ergonômica para gamers, com ajuste de altura e apoio para os braços', 1699.00, 15, 8, CURRENT_TIMESTAMP),
('Kindle Paperwhite', 'Leitor de livros digitais com tela de 6" e luz embutida', 499.00, 70, 9, CURRENT_TIMESTAMP),
('Notebook Dell Inspiron 15', 'Notebook Dell com processador Intel Core i7, 16GB de RAM e 512GB SSD', 4999.00, 20, 2, CURRENT_TIMESTAMP),
('Fone de Ouvido JBL Tune 500', 'Fone de ouvido bluetooth com até 16 horas de autonomia', 179.00, 120, 4, CURRENT_TIMESTAMP),
('Camera DSLR Canon EOS Rebel T7', 'Câmera DSLR com lente 18-55mm, resolução de 24.1 MP e gravação de vídeo em Full HD', 3499.00, 40, 5, CURRENT_TIMESTAMP),
('Aspirador de Pó Vertical Philips PowerPro', 'Aspirador de pó vertical sem fio com tecnologia ciclônica', 899.00, 25, 3, CURRENT_TIMESTAMP),
('Caixa de Som Bluetooth JBL Charge 5', 'Caixa de som portátil com até 20 horas de autonomia e resistência à água', 999.00, 50, 4, CURRENT_TIMESTAMP),
('Geladeira Brastemp Inverse 480L', 'Geladeira Brastemp Inverse, com capacidade de 480L e tecnologia Frost Free', 2999.00, 15, 5, CURRENT_TIMESTAMP),
('Máquina de Lavar LG 10kg', 'Máquina de lavar LG com 10kg de capacidade, tecnologia de lavagem TurboWash', 1899.00, 30, 3, CURRENT_TIMESTAMP),
('Câmera de Segurança Intelbras 4K', 'Câmera de segurança Intelbras com imagem 4K e visão noturna', 1199.00, 20, 5, CURRENT_TIMESTAMP),
('Smartwatch Amazfit GTR 3', 'Smartwatch com tela AMOLED, monitoramento de saúde e GPS integrado', 1499.00, 40, 7, CURRENT_TIMESTAMP),
('Conjunto de Panelas Tramontina 5 Peças', 'Conjunto de panelas Tramontina com 5 peças, em aço inox', 349.00, 60, 8, CURRENT_TIMESTAMP),
('Impressora HP DeskJet 2710', 'Impressora multifuncional com Wi-Fi e compatível com cartuchos de tinta HP', 299.00, 70, 9, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

