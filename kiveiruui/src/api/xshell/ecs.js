import request from "@/utils/request";

//创建会话
export function addEcs(data) {
    return request({
        url: '/ecs',
        method: 'post',
        data: data
    });
}

//更新会话
export function updateEcs(data) {
    return request({
        url: '/ecs',
        method: 'put',
        data: data
    });
}

//删除会话
export function deleteEcs(id) {
    return request({
        url: '/ecs',
        method: 'delete',
        data:id
    });
}

//获取远程连接树
export function getEcsTree() {
    return request({
        url: '/ecs/tree',
        method: 'get'
    });
}