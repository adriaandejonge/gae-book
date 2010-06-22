package com.appspot.cache;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ETagCacheServlet extends HttpServlet {


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

        MemcacheService cache = MemcacheServiceFactory
        .getMemcacheService();

    String cacheKey = request.getPathInfo() + "." + "etag";
    String result = null;

    if (!cache.contains(cacheKey)) {

      String etag = Long.toString(System.currentTimeMillis());
      response.setHeader("ETag", etag);
      result = "Loaded into cache at " + (new Date());
      cache.put(cacheKey, etag, Expiration.byDeltaSeconds(120));
      response.getWriter().write(result);
    } else {
      response.setStatus(304);
    }


  }
}