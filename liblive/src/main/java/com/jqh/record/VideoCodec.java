package com.jqh.record;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;

import java.nio.ByteBuffer;

public class VideoCodec {

    private Handler handler;
    private MediaMuxer mediaMuxer;
    private MediaCodec mediaCodec;
    private int videoTrack;
    private boolean isRecording;

    public void startRecoding(String path, int width, int height, int degress){
        try{
            MediaFormat format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height);
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
            format.setInteger(MediaFormat.KEY_BIT_RATE, 500_000);
            format.setInteger(MediaFormat.KEY_FRAME_RATE, 20);
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 2);
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            mediaCodec.configure(format, null, null,MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();

            mediaMuxer = new MediaMuxer(path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            mediaMuxer.setOrientationHint(degress);
        }catch (Exception e){
            e.printStackTrace();
        }

        HandlerThread thread = new HandlerThread("videoCodec");
        thread.start();

        handler = new Handler(thread.getLooper());
        isRecording = true;
    }

    public void stopRecording(){
        isRecording = false;
      mediaMuxer.stop();
      mediaMuxer.release();
    }

    public boolean isRecording () {return isRecording;}

    public void queueEncode(final byte[] buffer) {
        if (!isRecording) return;
        handler.post(new Runnable() {
            @Override
            public void run() {
                int index = mediaCodec.dequeueInputBuffer(0);
                if (index > 0) {
                    ByteBuffer inputBuffer = mediaCodec.getInputBuffer(index);
                    inputBuffer.clear();
                    inputBuffer.put(buffer, 0, buffer.length);
                    mediaCodec.queueInputBuffer(index, 0, buffer.length, System.nanoTime() / 1000, 0);
                }
                while (true){
                    MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                    int encoderStatus = mediaCodec.dequeueOutputBuffer(bufferInfo, 10_1000);
                    if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER){
                        break;
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED){
                        MediaFormat newFormat = mediaCodec.getOutputFormat();
                        videoTrack = mediaMuxer.addTrack(newFormat);
                        mediaMuxer.start();
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED){
                        // 忽略
                    }  else {
                        ByteBuffer encodeData = mediaCodec.getOutputBuffer(encoderStatus);
                        if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                            bufferInfo.size = 0 ;// 配置信息不用管
                        }
                        if (bufferInfo.size != 0) {
                            encodeData.position(bufferInfo.offset);
                            encodeData.limit(bufferInfo.offset + bufferInfo.size);
                            mediaMuxer.writeSampleData(videoTrack, encodeData, bufferInfo);
                        }
                        mediaCodec.releaseOutputBuffer(encoderStatus, false);
                    }
                }
            }
        });
    }
}
