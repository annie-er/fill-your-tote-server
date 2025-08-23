-- tote
INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Always here', 'always-here', 'The perfect bag to bring with you on grocery runs.', '/tote/always-here.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'always-here');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Cat Cafe', 'cat-cafe', 'Enjoy the small moments.', '/tote/cat-cafe.jpeg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'cat-cafe');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Mer-cat', 'mer-cat', 'A reminder of the irony and futility of jealousy.', '/tote/cat-mermaid.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'mer-cat');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Cat Walk', 'cat-walk', 'Cat Walk!.', '/tote/cat-walk.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'cat-walk');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Connected to God', 'connected-to-god', 'Commissioned for The Church Assembly in Toronto''s summer camp. A reminder of God''s gentle presence in life.', '/tote/connected-to-god.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'connected-to-god');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Cool Cat Club', 'cool-cat-club', 'Cool cats only: brash driving... no scaredy cats allowed!', '/tote/cool-cat-club.jpg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'cool-cat-club');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Furever', 'furever', 'Friendship rules.', '/tote/furever.jpg', 22.99
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'furever');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Love myself', 'love-myself', 'Come as you are.', '/tote/love-myself.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'love-myself');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Cuute Cats', 'cuute-cats', 'meeeoow.', '/tote/photobooth.jpg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'cuute-cats');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Project Blaze', 'project-blaze', 'Commissioned by the Richmond Hill Fire Services for their three-day summer camp, Project Blaze. Project Blaze offers an environment that empowers young women to confidently pursue a career in the Fire services.', '/tote/project-blaze.jpeg', 11.00
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'project-blaze');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Reflection', 'reflection', 'Who are you, really?', '/tote/reflection.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'reflection');

INSERT INTO product (name, slug, description, image_url, price)
SELECT 'Grasping Roses', 'grasping-roses', 'Personal illustration about beauty standards.', '/tote/rose-hand.jpeg', 13.50
    WHERE NOT EXISTS (SELECT 1 FROM product WHERE slug = 'grasping-roses');

-- drawing
INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Cats On a Roll!', 'cats-on-a-roll','Crazy, fun cats.', '/drawing/cats-on-a-roll.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'cats-on-a-roll');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'View From My Window', 'view-from-my-window', 'Looking through my window, I feel life''s wide and vast expanse.', '/drawing/window.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'view-from-my-window');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Sun Beam', 'sun-beam', 'In the sun, I feel comforted.', '/drawing/sun.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'sun-beam');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Bro Chilling', 'bro-chilling', 'Brother relaxing on beach chair.', '/drawing/bro-chillin.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'bro-chilling');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'View Of My Classroom', 'view-of-my-classroom', 'Doodle done during the mundane hours of Civics class.', '/drawing/classroom.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'view-of-my-classroom');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'My Roses', 'my-roses', 'Beauty standards, it destroys me.', '/drawing/grasp.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'my-roses');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'The Great Surf!', 'the-great-surf', 'Watch!', '/drawing/great-surf.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'the-great-surf');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Mom Chilling', 'mom-chilling', 'Mom chilling through the chaos of life.', '/drawing/mom-chillin.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'mom-chilling');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Dissatisfied Mer-cats', 'dissatisfied-mer-cats', 'Mer-cats, so much like humans.', '/drawing/dissatisfaction.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'dissatisfied-mer-cats');

INSERT INTO drawing (name, slug, description, image_url)
SELECT 'Friends Chilling', 'friends-chilling', 'Lunch break with friends.', '/drawing/friends.png'
    WHERE NOT EXISTS (SELECT 1 FROM drawing WHERE slug = 'friends-chilling');