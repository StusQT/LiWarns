# LiWarns
Плагин, добавляющий предупреждения в майнкрафт

**Конфигурация:**
- `maxWarnings`
  - Количество предупреждений, после которых игрок будет кикнут с сервера
  - Значение по умолчанию: *3*
  
- `kickMessage`
  - Сообщение, которое будет отображаться игроку, в случае кика
  - Значение по умолчанию: *Excuse me, you got too many warnings and got kicked out*

***

**Команды:**
- `/warn [player] [cause]`
  - Добавляет предупреждение игроку. Вам требуется указать причину, по которой было выдано предупреждение.
    Если количество предупреждений превышает превышает значение, указанное в конфигурации - игрок будет кикнут
    
- `/unwarn [player]`
  - Снимает последнее предупреждение с игрока
  
- `/warnlist`
  - Создаёт виртуальную книгу со списком предупреждений и отображает её игроку. <br>
  Если отображение книги невозможно (встречается на старых версиях minceraft) - отправляет список предупреждений в чат.
  
  ***
  **Разрешения**
- `li_warns.*`
  - Даёт разрешение выдавать и удалять предупреждения
- `li_warns.warn`
  - Даёт разрешение выдавать предупреждения
- `li_warns.unwarn`
  - Даёт разрешение удалять предупреждения
    
***
**Хранение данных**
- Все вызовы `/warn` и `/unwarn` логируются в файл `warns.log`

- Данные хранятся в файле конфигурации `warns.yml`<br>
Примерный вид:
```yaml
players:
  67128b5b-2e6b-3ad1-baa0-1b937b03e5c5:
    warnCount: 2
    warnings:
    - ==: PlayerWarning
      adminName: SomeName
      cause: Warning cause
      time: '2020-11-27T14:47:46.810'
    - ==: PlayerWarning
      adminName: SomeName
      cause: Other warning cause
      time: '2020-11-27T14:48:25.130'
```

В будущем планируется добавление поддержки базы данных `MySQL`
(будет создана еще одна, параллельно развивающееся версия плагина)
***
На данным момент плагин доступен только на английском языке.
