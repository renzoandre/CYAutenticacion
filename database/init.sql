CREATE TABLE "public"."users" (
                                  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
                                  "name" varchar NOT NULL,
                                  "last_name_1" varchar NOT NULL,
                                  "last_name_2" varchar NOT NULL,
                                  "birth_date" timestamp,
                                  "address" varchar,
                                  "phone" varchar,
                                  "email" varchar NOT NULL,
                                  "base_salary" float8 NOT NULL,
                                  "active" bool NOT NULL DEFAULT true,
                                  PRIMARY KEY ("id")
);

INSERT INTO "public"."users" ("id", "name", "last_name_1", "last_name_2", "birth_date", "address", "phone", "email", "base_salary", "active") VALUES
    (gen_random_uuid(), "Renzo Andre", "Condo", "Miranda", NOW(), "Dirección de renzo", "987654321", "renzoandrecm@gmail.com", 10000, TRUE);