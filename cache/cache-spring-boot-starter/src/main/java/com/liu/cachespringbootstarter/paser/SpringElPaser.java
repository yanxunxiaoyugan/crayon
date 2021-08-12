package com.liu.cachespringbootstarter.paser;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.liu.cachespringbootstarter.annotation.Magic;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.constants.SignalConstant;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;

/**
 * springEL表达式解析器
 */
public class SpringElPaser implements Ipaser {
	private final ExpressionParser parser = new SpelExpressionParser();
	// 每次编译Expression都比较慢，所以这里吧expression给缓存起来
	private final ConcurrentHashMap<String, Expression> expressionConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Method> methodConcurrentHashMap = new ConcurrentHashMap<>();

	@Override
	public CacheKeyDto getCacheAbleKey(MethodInvocation methodInvocation,
			CacheAble cacheAble) {
		String s = this.doGetCacheKey(methodInvocation.getThis(), methodInvocation.getMethod().getName(),
				methodInvocation.getArguments(), cacheAble.key(), null, null, false);
		return new CacheKeyDto(s,null);

	}

	@Override
	public CacheKeyDto getCacheMagicKey(MethodInvocation methodInvocation, Magic magic,Object returnValue) {
		String s = this.doGetCacheKey(methodInvocation.getThis(), methodInvocation.getMethod().getName(), methodInvocation.getArguments(),
				magic.key(), null, returnValue, true);
		return new CacheKeyDto(s,returnValue);
	}

	/**
	 * @param target
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param arguments
	 *            方法参数
	 * @param keyExpression
	 *            key表达式
	 * @param hfieldExpression
	 *            hfield表达式
	 * @param returnValue
	 *            方法的返回结果
	 * @param hasRetVal
	 *            是否有返回值
	 * @return
	 */
	private String doGetCacheKey(Object target, String methodName,
			Object[] arguments, String keyExpression, String hfieldExpression,
			Object returnValue, boolean hasRetVal) {
		// 如果CacheAble上的key不为空
		if (StringUtils.isNotBlank(keyExpression)) {
			// 如果不是表达式,直接返回
			if (!keyExpression.contains(SignalConstant.POUND)
					&& !keyExpression.contains(SignalConstant.apostrophe)) {
				return keyExpression;
			}
		}
		StandardEvaluationContext context = new StandardEvaluationContext();
		//把参数设置进去
		context.setVariable(SignalConstant.args,arguments);
		context.setVariable(SignalConstant.RET_VAL,returnValue);
		for (Map.Entry<String, Method> entry : methodConcurrentHashMap
				.entrySet()) {
			context.registerFunction(entry.getKey(), entry.getValue());
		}
		if (hasRetVal) {
			context.setVariable(SignalConstant.RET_VAL, returnValue);
		}
		Expression expression = expressionConcurrentHashMap.get(keyExpression);
		if (null == expression) {
			expression = parser.parseExpression(keyExpression);
			expressionConcurrentHashMap.put(keyExpression, expression);
		}
		return expression.getValue(context,String.class);
	}
}
