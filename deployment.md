# 🚀 Deployment Guide: Connectinc CRM

To get your app live on the internet, we will use **Render.com**. It's free, stable, and easy to use.

---

## Step 0: Push your code to GitHub
Make sure your latest code (with the `Dockerfile` and `app.js` changes) is pushed to a **Public** or **Private** GitHub repository.

---

## Step 1: Create a PostgreSQL Database
1.  Go to [Render Dashboard](https://dashboard.render.com/).
2.  Click **New +** -> **PostgreSQL**.
3.  Name it `chinspring-db`.
4.  Click **Create Database**.
5.  **Important**: Copy the **Internal Database URL** (for the backend) and **External Database URL** (for your own tools/debugging).

---

## Step 2: Deploy the Backend (Spring Boot)
1.  Click **New +** -> **Web Service**.
2.  Connect your GitHub repository.
3.  **Name**: `chinspring-api`.
4.  **Runtime**: **Docker**.
5.  Scroll down to **Environment Variables** and add:
    -   `SPRING_DATASOURCE_URL` = (Your Render Internal DB URL)
    -   `SPRING_DATASOURCE_USERNAME` = (Your DB Username)
    -   `SPRING_DATASOURCE_PASSWORD` = (Your DB Password)
    -   `SPRING_JPA_HIBERNATE_DDL_AUTO` = `update`
6.  Click **Deploy Web Service**.

---

## Step 3: Deploy the Frontend (Vanilla JS)
1.  Click **New +** -> **Static Site**.
2.  Connect the same GitHub repository.
3.  **Name**: `connectinc-crm`.
4.  **Build Command**: `cd frontend && npm install && npm run build`
5.  **Publish Directory**: `frontend/dist`
6.  Click **Deploy Static Site**.

---

## Step 4: Final Connection 🔌
Once your backend is live, you will get a URL (like `https://chinspring-api.onrender.com`).
1.  Open `frontend/app.js` on your computer.
2.  Update the `apiBase` variable at the top to your new Render URL.
3.  `git commit` and `git push` again. 
4.  Render will automatically detect the change and redeploy your frontend!

---
**You're Live!** Share your URL and start managing your clients from anywhere! 🌍
