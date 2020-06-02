insert into api_keys(id, name, type, api_key, default_key)
values (1, 'Default WebPageTest', 'WEB_PAGE_TEST', null, true),
       (2, 'Default PageSpeed', 'PAGE_SPEED', null, true)
on conflict do nothing;
