CREATE TABLE "members" (
    "id" BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL UNIQUE,
    "password" CHAR(64) NOT NULL,
    "email" VARCHAR(255) NOT NULL UNIQUE,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);

CREATE TABLE "posts" (
    "id" BIGSERIAL PRIMARY KEY,
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members"("id"),
    "description" TEXT,
    "image_url" TEXT,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);

CREATE TABLE "likes" (
    "post_id" UUID NOT NULL REFERENCES "posts" ("id"),
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members" ("id"),
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("post_id", "member_id")
);

CREATE TABLE "comments" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "post_id" UUID NOT NULL REFERENCES "posts" ("id"),
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members" ("id"),
    "content" TEXT NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);