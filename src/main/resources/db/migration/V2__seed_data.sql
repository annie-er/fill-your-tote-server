-- Product seed data
INSERT INTO product (name, slug, description, image_url, price)
VALUES
    ('Always here', 'always-here', 'The perfect bag to bring with you on grocery runs.', 'https://fillyourtotestorage.blob.core.windows.net/totes/always-here.png', 22.99),
    ('Cat Cafe', 'cat-cafe', 'Enjoy the small moments.', 'https://fillyourtotestorage.blob.core.windows.net/totes/cat-cafe.jpeg', 22.99),
    ('Mer-cat', 'mer-cat', 'A reminder of the irony and futility of jealousy.', 'https://fillyourtotestorage.blob.core.windows.net/totes/cat-mermaid.jpeg', 13.50),
    ('Cat Walk', 'cat-walk', 'Cat Walk!', 'https://fillyourtotestorage.blob.core.windows.net/totes/cat-walk.jpg', 22.99),
    ('Connected to God', 'connected-to-god', 'Commissioned for The Church Assembly in Toronto''s summer camp. A reminder of God''s gentle presence in life.', 'https://fillyourtotestorage.blob.core.windows.net/totes/connected-to-god.jpeg', 13.50),
    ('Cool Cat Club', 'cool-cat-club', 'Cool cats only: brash driving... no scaredy cats allowed!', 'https://fillyourtotestorage.blob.core.windows.net/totes/cool-cat-club.jpg', 13.50),
    ('Furever', 'furever', 'Friendship rules.', 'https://fillyourtotestorage.blob.core.windows.net/totes/furever.jpg', 22.99),
    ('Love myself', 'love-myself', 'Come as you are.', 'https://fillyourtotestorage.blob.core.windows.net/totes/love-myself.jpeg', 13.50),
    ('Cuute Cats', 'cuute-cats', 'meeeoow.', 'https://fillyourtotestorage.blob.core.windows.net/totes/photobooth.jpg', 13.50),
    ('Project Blaze', 'project-blaze', 'Commissioned by the Richmond Hill Fire Services for their three-day summer camp, Project Blaze. Project Blaze offers an environment that empowers young women to confidently pursue a career in the Fire services.', 'https://fillyourtotestorage.blob.core.windows.net/totes/project-blaze.jpeg', 11.00),
    ('Reflection', 'reflection', 'Who are you, really?', 'https://fillyourtotestorage.blob.core.windows.net/totes/reflection.jpeg', 13.50),
    ('Grasping Roses', 'grasping-roses', 'Personal illustration about beauty standards.', 'https://fillyourtotestorage.blob.core.windows.net/totes/rose-hand.jpeg', 13.50)
    ON CONFLICT (slug) DO UPDATE
                              SET name = EXCLUDED.name,
                              description = EXCLUDED.description,
                              image_url = EXCLUDED.image_url,
                              price = EXCLUDED.price;

-- Drawing seed data
INSERT INTO drawing (name, slug, description, image_url)
VALUES
    ('View From My Window', 'view-from-my-window', 'Looking through my window, I feel life''s wide and vast expanse.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/window.png'),
    ('Sun Beam', 'sun-beam', 'In the sun, I feel comforted.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/sun.png'),
    ('Bro Chilling', 'bro-chilling', 'Brother relaxing on beach chair.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/bro-chillin.png'),
    ('View Of My Classroom', 'view-of-my-classroom', 'Doodle done during the mundane hours of Civics class.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/classroom.png'),
    ('My Roses', 'my-roses', 'Beauty standards, it destroys me.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/grasp.png'),
    ('Mom Chilling', 'mom-chilling', 'Mom chilling through the chaos of life.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/mom-chillin.png'),
    ('Dissatisfied Mer-cats', 'dissatisfied-mer-cats', 'Mer-cats, so much like humans.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/dissatisfaction.png'),
    ('Friends Chilling', 'friends-chilling', 'Lunch break with friends.', 'https://fillyourtotestorage.blob.core.windows.net/drawings/friends.png')
    ON CONFLICT (slug) DO UPDATE
                              SET name = EXCLUDED.name,
                              description = EXCLUDED.description,
                              image_url = EXCLUDED.image_url;
