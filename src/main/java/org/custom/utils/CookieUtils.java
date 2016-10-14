package org.custom.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
      /**
       * 设定Cookie
       * @param response Http请求
       * @param name     设定Cookie名
       * @param value    设定Cookie值
       * @param maxAge   设定Cookie生命周期(单位(秒))
       */
      public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
          cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
      }

      /**
       * 根据Cookie名字获取Cookie
       * @param request Http请求
       * @param name    取得用Cookie名
       * @return        取得用Cookie名对应的Cookie
       */
      public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
          Cookie cookie = (Cookie) cookieMap.get(name);
          return cookie;
        } else {
          return null;
        }
      }

      /**
       * 将Cookie转换为Map类型
       * @param request Http请求
       * @return        Cookie的Map类型
       */
      private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
          for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie);
          }
        }
        return cookieMap;
      }
}
