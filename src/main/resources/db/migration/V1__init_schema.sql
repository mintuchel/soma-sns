CREATE TABLE "members" (
    "id" VARCHAR(20) PRIMARY KEY,
    "password" CHAR(64) NOT NULL,
    "email" VARCHAR(255) NOT NULL UNIQUE,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);

CREATE TABLE "posts" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members"("id") ON DELETE CASCADE,
    "description" TEXT,
    "image_url" TEXT,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);

CREATE TABLE "likes" (
    "post_id" UUID NOT NULL REFERENCES "posts" ("id") ON DELETE CASCADE,
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members" ("id") ON DELETE CASCADE,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("post_id", "member_id")
);

CREATE TABLE "comments" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "post_id" UUID NOT NULL REFERENCES "posts" ("id") ON DELETE CASCADE,
    "member_id" VARCHAR(20) NOT NULL REFERENCES "members" ("id") ON DELETE CASCADE,
    "content" TEXT NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP
);