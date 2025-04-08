# AI-RAG-Knowledge 增强检索知识库 🌟
<br/> 作者：MasterHesse
> 一个基于领域驱动设计的智能知识增强系统，融合 **RAG 技术** 与多模态 AI 能力，实现高效的知识检索、语义解析与智能问答。

🐳 [Docker 镜像地址](https://hub.docker.com/masterhesse/ai-rag-knowledge-app)

## 🚀 核心功能

### 知识中枢
- **多源知识注入**  
  📤 支持 MD/TXT 知识库文件上传 & Git 仓库解析（自动抓取代码文档）
- **智能语义解析**  
  🧠 基于 Deepseek、GPT-4o 等的多层级内容理解
- **混合检索引擎**  
  🔍 结合 pgVector 相似性搜索 + 关键词检索的混合模式

### 智能交互
- ⚡ 实时问答系统，支持追问上下文追溯）
- 🔄 自动化的知识更新触发器

## 🛠️ 技术栈全景

| 领域                | 技术组件                          |
|---------------------|----------------------------------|
| **核心框架**         | SpringAI + JDK17 +Springboot                |
| **AI 引擎**          | Ollama Deepseek  |
| **数据存储**         | PostgreSQL + pgVector            |
| **缓存系统**         | Redis 7.x + Redis-commander      |
| **服务架构**         | DDD 分层架构                     |
| **部署运维**         | Nginx + Ubuntu22 云服务器        |

## 📦 系统架构

```text
📁 mh-dev-tech-api      → 接口适配层，负责定义接口

📁 mh-dev-tech-trigger  → 事件驱动层，负责实现接口

📁 mh-dev-tech-app       → 领域业务核心，负责配置和启动项
```
