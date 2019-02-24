# sb-scrape

####

    sb scrape, the greatest invention of mankind

    
    MYSQL URL:
    
    -   jdbc:mysql://localhost:3306
        
    TEST DATA:

    CREATE DATABASE sportsFive
    
    INSERT INTO user (user_id,username,email_address)
    VALUES (1,'newuser','unwoundcracker@gmail.com');
    
    INSERT INTO user (user_id,username,email_address,active)
    VALUES (2,'spankypants','unwoundcracker@gmail.com',1);
    
    INSERT INTO user_subscription (user_subscription_id, user_id, subscription_type_id)
    VALUES (1, 1, 1);
    
    INSERT INTO user_subscription (user_subscription_id, user_id, subscription_type_id)
    VALUES (2, 2, 1);
