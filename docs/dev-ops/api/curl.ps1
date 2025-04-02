# curl.ps1 内容
curl.exe http://117.72.53.2:11434/api/generate `
  -H "Content-Type: application/json" `
  -d '{
        "model": "deepseek-r1:1.5b",
        "prompt": "1+1",
        "stream": true
      }'