import request from "@/utils/request";

// 获取目录下文件列表
export function listFile(query) {
    return request({
        url: '/listFile',
        method: 'get',
        params: {
            userId: query.userId,
            path: query.path
        }
    });
}