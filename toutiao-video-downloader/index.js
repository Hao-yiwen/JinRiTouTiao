const express = require('express');
const axios = require('axios');
const cheerio = require('cheerio');

const app = express();
const PORT = process.env.PORT || 3000;

// 解析视频URL的函数
async function getVideoUrl(toutiaoUrl) {
  try {
    // 打印 URL，确保它是正确的
    console.log('Requesting URL:', toutiaoUrl);

    const response = await axios.get(toutiaoUrl, {
      headers: {
        'authority': 'www.ixigua.com',
        'method': 'GET',
        'scheme': 'https',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
        'Accept-Encoding': 'gzip, deflate, br, zstd',
        'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8',
        'Cache-Control': 'no-cache',
        'Cookie': '__ac_nonce=0669e902400b9db7a1cc8; __ac_signature=_02B4Z6wo00f018CG6bAAAIDAknnJqDxjh6vApu0AAJbIf0; csrf_session_id=467a3ef74e56c32133c1d48f403d4f3c; UIFID_TEMP=8d96e8235c027a4a9fec397caca3147e71dc8a9d4a23d117ff90baf5f273397d8b8d2166526ca117a28e24ae8c9da9594e5cd3c0887c9ba9019075f66d6a1a584b79c16c638d9ed40cc10622097f2fd604e57a2c4bc7e581d09a35d133363076; support_webp=true; support_avif=true; gfkadpd=1768,30523; x-web-secsdk-uid=fc6b517a-6399-4ef7-a474-49df18e34fa4; first_enter_player=%7B%22any_video%22%3A%222.14.0-1%22%7D; fpk1=U2FsdGVkX1/P9xKFqO+J6lHK0nUGIBgAS90ndZISLzfLSpVJbeQ4Qu+Om57b+mhSGEPEfav/iIanqn9SqAcYnQ==; fpk2=10f9287deaf609ee36fb37783f2b89c0; UIFID=8d96e8235c027a4a9fec397caca3147e71dc8a9d4a23d117ff90baf5f273397d8b8d2166526ca117a28e24ae8c9da95994719262e7604710c780a16f4599f0d15b05d0799417e31e0919a2e019c2c8798bd988e1b8c91bfb473ebb53e69f6d6ab81a80cc02cabfca934b6fd784b16fb4385e15144557641809e43c7a5cc8f19a; _tea_utm_cache_2285=undefined; ixigua-a-s=1; ttwid=1%7Czfl2j27ObrRxE93-FFEku0t5lkpmdhyhwwDpHEEhXdc%7C1721667739%7Cb58a2a43f1bc7744124caac3089f538f945bd4c9e870242cda3c073bf620aaf6',
        'Pragma': 'no-cache',
        'Priority': 'u=0, i',
        'Sec-Ch-Ua': '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
        'Sec-Ch-Ua-Mobile': '?0',
        'Sec-Ch-Ua-Platform': '"macOS"',
        'Sec-Fetch-Dest': 'document',
        'Sec-Fetch-Mode': 'navigate',
        'Sec-Fetch-Site': 'none',
        'Sec-Fetch-User': '?1',
        'Upgrade-Insecure-Requests': '1',
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'
      }
    });

    // 打印响应状态码和头部，帮助调试
    console.log('Response status:', response.status);
    console.log('Response headers:', response.headers);

    const $ = cheerio.load(response.data);

    // 解析视频URL，这里假设视频URL在 <video> 标签的 src 属性中
    const videoUrl = $('video').attr('src');
    return videoUrl;
  } catch (error) {
    console.error('Error fetching video URL:', error.message);
    return null;
  }
}

// 定义一个API端点来获取视频URL
app.get('/get-video-url', async (req, res) => {
  const { url } = req.query;
  if (!url) {
    return res.status(400).send('URL is required');
  }

  const videoUrl = await getVideoUrl(url);
  if (videoUrl) {
    res.json({ videoUrl });
  } else {
    res.status(404).send('Video URL not found');
  }
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});