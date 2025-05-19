package com.demo.backend.util;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
public class HuoShanUtil {

    public static Result Chat(String apiKey, String content,String modelName) {
        // 创建ArkService实例
        ArkService arkService = ArkService.builder().apiKey(apiKey).build();
        // 初始化消息列表
        List<ChatMessage> chatMessages = new ArrayList<>();
        String constraint="SYSTEM: 你是一个LaTeX格式修正器，必须严格遵守以下规则：\n" +
                "1. 输入将被直接处理，输出必须是原始LaTeX代码\n" +
                "2. 禁止添加任何额外符号（包括$、`、```等所有非LaTeX字符）\n" +
                "3. 禁止任何解释性文字（包括\"修改后的代码是：\"等前缀）\n" +
                "4. 如果输入已经是合规LaTeX，原样返回\n" +
                "5. 输出必须与输入保持相同的语义内容\n" +
                "6. 违规示例（禁止出现）：\n" +
                "   - 错误：$x^2$ → 正确：x^2\n" +
                "   - 错误：```\\section{test}``` → 正确：\\section{test}\n" +
                "7. 输出只能是以下两种形式之一：\n" +
                "   a) 修正后的LaTeX代码（当发现格式问题时）\n" +
                "   b) 完全相同的输入内容（当无格式问题时）\n" +
                "\n" +
                "USER: [待处理的LaTeX代码]\n";
        // 创建用户消息
        ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(constraint+ content)
                .build();
        chatMessages.add(userMessage);

        // 创建聊天完成请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(getModelID(modelName))
                .messages(chatMessages)
                .build();

        StringBuilder contentBuilder = new StringBuilder();
        try {
            arkService.streamChatCompletion(chatCompletionRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach(choice -> {
                        // 检查是否存在有效响应
                        if (choice.getChoices() != null && !choice.getChoices().isEmpty()) {
                            ChatMessage message = choice.getChoices().get(0).getMessage();
                            if (message.getContent() != null) {
                                contentBuilder.append(message.getContent());
                            }
                        }
                    });

            // 检查是否成功获取内容
            if (contentBuilder.length() > 0) {
                log.info(contentBuilder.toString());
                return Result.success(contentBuilder.toString());
            } else {
                return Result.fail("未收到有效响应内容");
            }
        } catch (Exception e) {
            return Result.fail("请求失败: " + e.getMessage());
        } finally {
            // 确保关闭资源
            arkService.shutdownExecutor();
        }
    }
    public static String getModelID(String modelName){
        return Constants.get(modelName);
    }
}

