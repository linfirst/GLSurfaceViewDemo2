package com.example.glsurfaceviewdemo2

/**
 * https://juejin.cn/post/7080732149388673060
 */
object FragmentShaderCode {

    /**
     * 灰度片段着色器代码
     */
    val grayFragmentShaderCode = """
    varying highp vec2 textureCoordinate;
    uniform sampler2D inputImageTexture;
    void main() {
        gl_FragColor = texture2D(inputImageTexture, textureCoordinate);
        float average = 0.2126 * gl_FragColor.r + 0.7152 * gl_FragColor.g + 0.0722 * gl_FragColor.b;
        gl_FragColor = vec4(average, average, average, 1.0);
    }
    """

    /**
     * 反相-片段着色器代码
     */
    val reverseFragmentShaderCode ="""
    varying highp vec2 textureCoordinate;
    uniform sampler2D inputImageTexture;
    void main() {
        gl_FragColor = vec4(vec3(1.0 - texture2D(inputImageTexture, textureCoordinate)), 1.0);
    }
"""
    /**
     *模糊-片段着色器代码
     */
    val blurFragmentShaderCode = """
    varying highp vec2 textureCoordinate;
    uniform sampler2D inputImageTexture;
    const float offset = 1.0f / 300.0f;
    void main() {
        // 核效果取周围像素值的偏移量
        vec2 offsets[9];
        offsets[0] = vec2(-offset,  offset); // 左上
        offsets[1] = vec2( 0.0f,    offset); // 正上
        offsets[2] = vec2( offset,  offset); // 右上
        offsets[3] = vec2(-offset,  0.0f);   // 左
        offsets[4] = vec2( 0.0f,    0.0f);   // 中
        offsets[5] = vec2( offset,  0.0f);   // 右
        offsets[6] = vec2(-offset, -offset); // 左下
        offsets[7] = vec2( 0.0f,   -offset); // 正下
        offsets[8] = vec2( offset, -offset);  // 右下
        // 核函数
        float kernel[9];
        kernel[0] = 1.0f / 16.0f;
        kernel[1] = 2.0f / 16.0f;
        kernel[2] = 1.0f / 16.0f;
        kernel[3] = 2.0f / 16.0f;
        kernel[4] = 4.0f / 16.0f;
        kernel[5] = 2.0f / 16.0f;
        kernel[6] = 1.0f / 16.0f;
        kernel[7] = 2.0f / 16.0f;
        kernel[8] = 1.0f / 16.0f;
        // 计算采样值
        vec3 sampleTex[9];
        for(int i = 0; i < 9; i++)
        {
            sampleTex[i] = vec3(texture2D(inputImageTexture, textureCoordinate.xy + offsets[i]));
        }
        vec3 col = vec3(0.0);
        for(int i = 0; i < 9; i++)
            col += sampleTex[i] * kernel[i];

        gl_FragColor = vec4(col, 1.0);
    }
"""

    /**
     * 锐化-片段着色器代码
     */
    val sharpenFragmentShaderCode = """
    varying highp vec2 textureCoordinate;
    uniform sampler2D inputImageTexture;
    const float offset = 1.0f / 300.0f;
    void main() {
        // 核效果取周围像素值的偏移量
        vec2 offsets[9];
        offsets[0] = vec2(-offset,  offset); // 左上
        offsets[1] = vec2( 0.0f,    offset); // 正上
        offsets[2] = vec2( offset,  offset); // 右上
        offsets[3] = vec2(-offset,  0.0f);   // 左
        offsets[4] = vec2( 0.0f,    0.0f);   // 中
        offsets[5] = vec2( offset,  0.0f);   // 右
        offsets[6] = vec2(-offset, -offset); // 左下
        offsets[7] = vec2( 0.0f,   -offset); // 正下
        offsets[8] = vec2( offset, -offset);  // 右下
        // 核效果
        float kernel[9];
        kernel[0] = -1.0f;
        kernel[1] = -1.0f;
        kernel[2] = -1.0f;
        kernel[3] = -1.0f;
        kernel[4] = 9.0f;
        kernel[5] = -1.0f;
        kernel[6] = -1.0f;
        kernel[7] = -1.0f;
        kernel[8] = -1.0f;
        // 计算采样值
        vec3 sampleTex[9];
        for(int i = 0; i < 9; i++)
        {
            sampleTex[i] = vec3(texture2D(inputImageTexture, textureCoordinate.xy + offsets[i]));
        }
        vec3 col = vec3(0.0);
        for(int i = 0; i < 9; i++)
            col += sampleTex[i] * kernel[i];

        gl_FragColor = vec4(col, 1.0);
    }
"""

    /**
     * 边缘检测-片段着色器代码
     */
    val edgeDetectionfragmentShaderCode = """
    varying highp vec2 textureCoordinate;
    uniform sampler2D inputImageTexture;
    const float offset = 1.0f / 300.0f;
    void main() {
        // 核效果取周围像素值的偏移量
        vec2 offsets[9];
        offsets[0] = vec2(-offset,  offset); // 左上
        offsets[1] = vec2( 0.0f,    offset); // 正上
        offsets[2] = vec2( offset,  offset); // 右上
        offsets[3] = vec2(-offset,  0.0f);   // 左
        offsets[4] = vec2( 0.0f,    0.0f);   // 中
        offsets[5] = vec2( offset,  0.0f);   // 右
        offsets[6] = vec2(-offset, -offset); // 左下
        offsets[7] = vec2( 0.0f,   -offset); // 正下
        offsets[8] = vec2( offset, -offset);  // 右下
        // 核效果
        float kernel[9];
        kernel[0] = 1.0f;
        kernel[1] = 1.0f;
        kernel[2] = 1.0f;
        kernel[3] = 1.0f;
        kernel[4] = -8.0f;
        kernel[5] = 1.0f;
        kernel[6] = 1.0f;
        kernel[7] = 1.0f;
        kernel[8] = 1.0f;
        // 计算采样值
        vec3 sampleTex[9];
        for(int i = 0; i < 9; i++)
        {
            sampleTex[i] = vec3(texture2D(inputImageTexture, textureCoordinate.xy + offsets[i]));
        }
        vec3 col = vec3(0.0);
        for(int i = 0; i < 9; i++)
            col += sampleTex[i] * kernel[i];

        gl_FragColor = vec4(col, 1.0);
    }
"""


}