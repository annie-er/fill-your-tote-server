-- tote
INSERT INTO product (name, description, image_url, price)
SELECT 'Always here', 'The perfect bag to bring with you on grocery runs.', '/static/tote/always-here.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Always here');

INSERT INTO product (name, description, image_url, price)
SELECT 'Cat Cafe', 'Enjoy the small moments.', '/static/tote/cat-cafe.jpeg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Cat Cafe');

INSERT INTO product (name, description, image_url, price)
SELECT 'Mer-cat', 'A reminder of the irony and futility of jealousy.', '/static/tote/cat-mermaid.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Mer-cat');

INSERT INTO product (name, description, image_url, price)
SELECT 'Cat Walk', 'Cat Walk!.', '/static/tote/cat-walk.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Cat Walk');

INSERT INTO product (name, description, image_url, price)
SELECT 'Connected to God', 'Commissioned for The Church Assembly in Toronto''s summer camp. A reminder of God''s gentle presence in life.', '/static/tote/connected-to-god.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Connected to God');

INSERT INTO product (name, description, image_url, price)
SELECT 'Cool Cat Club', 'Cool cats only: brash driving... no scaredy cats allowed!', '/static/tote/cool-cat-club.jpg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Cool Cat Club');

INSERT INTO product (name, description, image_url, price)
SELECT 'Furever', 'Friendship rules.', '/static/tote/furever.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Furever');

INSERT INTO product (name, description, image_url, price)
SELECT 'Love myself', 'Come as you are.', '/static/tote/love-myself.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Love myself');

INSERT INTO product (name, description, image_url, price)
SELECT 'Cuute Cats', 'meeeoow.', '/static/tote/photobooth.jpg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Cuute Cats');

INSERT INTO product (name, description, image_url, price)
SELECT 'Project Blaze', 'Commissioned by the Richmond Hill Fire Services for their three-day summer camp, Project Blaze. Project Blaze offers an environment that empowers young women to confidently pursue a career in the Fire services.', '/static/tote/project-blaze.jpeg', 11.00
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Project Blaze');

INSERT INTO product (name, description, image_url, price)
SELECT 'Reflection', 'Who are you, really?', '/static/tote/reflection.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Reflection');

INSERT INTO product (name, description, image_url, price)
SELECT 'Grasping Roses', 'Personal illustration about beauty standards.', '/static/tote/rose-hand.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE name = 'Grasping Roses');

-- drawing
INSERT INTO drawing (name, description, image_url)
SELECT 'Cats On a Roll!', 'Crazy, fun cats.', '/static/drawing/cats-on-a-roll.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Cats On a Roll!');

INSERT INTO drawing (name, description, image_url)
SELECT 'View From My Window', 'Looking through my window, I feel life''s wide and vast expanse.', '/static/drawing/window.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'View From My Window');

INSERT INTO drawing (name, description, image_url)
SELECT 'Sun Beam', 'In the sun, I feel comforted.', '/static/drawing/sun.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Sun Beam');

INSERT INTO drawing (name, description, image_url)
SELECT 'Bro Chilling', 'Brother relaxing on beach chair.', '/static/drawing/bro-chillin.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Bro Chilling');

INSERT INTO drawing (name, description, image_url)
SELECT 'View Of My Classroom', 'Doodle done during the mundane hours of Civics class.', '/static/drawing/classroom.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'View Of My Classroom');

INSERT INTO drawing (name, description, image_url)
SELECT 'My Roses', 'Beauty standards, it destroys me.', '/static/drawing/grasp.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'My Roses');

INSERT INTO drawing (name, description, image_url)
SELECT 'The Great Surf!', 'Watch!', '/static/drawing/great-surf.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'The Great Surf!');

INSERT INTO drawing (name, description, image_url)
SELECT 'Mom Chilling', 'Mom chilling through the chaos of life.', '/static/drawing/mom-chillin.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Mom Chilling');

INSERT INTO drawing (name, description, image_url)
SELECT 'Dissatisfied Mer-cats', 'Mer-cats, so much like humans.', '/static/drawing/dissatisfaction.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Dissatisfied Mer-Cats');

INSERT INTO drawing (name, description, image_url)
SELECT 'Friends Chilling', 'Lunch break with friends.', '/static/drawing/friends.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE name = 'Friends Chilling');