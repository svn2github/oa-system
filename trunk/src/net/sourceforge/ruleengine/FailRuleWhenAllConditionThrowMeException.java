/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.ruleengine;

/**
 * 用于影响Rule执行的特殊Exception。
 * 对指定target执行flow时，Engine会读取特定的target对象属性来验证条件。
 * 如果验证rule中的每个条件时得检测到此异常抛出，则rule不通过，否则依据没有抛出异常的条件验证结果决定rule是否通过。
 *  
 * @author nicebean
 * @version 1.0 (2006-1-12)
 */
public class FailRuleWhenAllConditionThrowMeException extends RuntimeException {

    public FailRuleWhenAllConditionThrowMeException() {
        
    }

}
