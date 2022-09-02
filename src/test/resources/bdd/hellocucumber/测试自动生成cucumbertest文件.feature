Feature: 今天到周五了吗？

  Scenario Outline: 周日不是周五
    Given 今天是<周几1>
    When 我问今天是否为<周几2>
    Then 我应该被告知的是——<结果YN>
    Examples:
      | 周几1 | 周几2 | 结果YN |
      | 周五  | 周一  | N    |
